package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.filter.FilterQueryBuilder;
import com.epam.esm.repository.filter.QueryBuilderResult;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Implementation of {@link GiftCertificateRepository}
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT gift_certificate FROM GiftCertificate gift_certificate";
    private static final String EXIST_GIFT_CERTIFICATES_QUERY = "SELECT COUNT(gift_certificate) FROM GiftCertificate gift_certificate WHERE gift_certificate.name=:giftCertificateName";
    private static final String COUNT_ALL_GIFT_CERTIFICATES_QUERY = "SELECT COUNT(gift_certificate) FROM GiftCertificate gift_certificate";
    private static final String GIFT_CERTIFICATE_NAME = "giftCertificateName";
    private static final long EMPTY_COUNT_OF_GIFT_CERTIFICATE = 0L;

    @PersistenceContext
    private EntityManager entityManager;

    private final FilterQueryBuilder filterQueryBuilder;
    private QueryBuilderResult queryBuilderResult;

    @Autowired
    public GiftCertificateRepositoryImpl(FilterQueryBuilder filterQueryBuilder, QueryBuilderResult queryBuilderResult) {
        this.filterQueryBuilder = filterQueryBuilder;
        this.queryBuilderResult = queryBuilderResult;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findAll(Integer page, Integer size) {
        return entityManager.createQuery(FIND_ALL_GIFT_CERTIFICATES_QUERY, GiftCertificate.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    @Override
    public List<GiftCertificate> findWithFilter(Integer page, Integer size, GiftCertificateFilterCondition giftCertificateFilterCondition) {
        queryBuilderResult = filterQueryBuilder.buildQuery(giftCertificateFilterCondition);
        Query query = entityManager.createNativeQuery(queryBuilderResult.getQuery());
        Set<Map.Entry<String, String>> entries = queryBuilderResult.getParameters().entrySet();
        for (Map.Entry<String, String> e : entries) {
            query.setParameter(e.getKey(), e.getValue());
        }
        List<Object> resultList = query.setFirstResult((page - 1) * size)
                .setMaxResults(size).getResultList();
        return getFilteredGiftCertificatesFromResultList(resultList);
    }

    private List<GiftCertificate> getFilteredGiftCertificatesFromResultList(List<Object> resultList) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Object result : resultList) {
            long id = Long.parseLong(result.toString());
            giftCertificates.add(findById(id));
        }
        return giftCertificates;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate certificate = Optional.ofNullable(entityManager.find(GiftCertificate.class, id))
                .orElseThrow(() -> new RepositoryException("gift.certificate.not.found", id));
        entityManager.remove(certificate);
    }

    @Override
    public int countAll() {
        return entityManager.createQuery(COUNT_ALL_GIFT_CERTIFICATES_QUERY, Long.class).getSingleResult().intValue();
    }

    @Override
    public boolean existsGiftCertificateByName(String name) {
        TypedQuery<Long> query = entityManager.createQuery(EXIST_GIFT_CERTIFICATES_QUERY, Long.class);
        query.setParameter(GIFT_CERTIFICATE_NAME, name);
        return query.getResultStream().findFirst().orElse(EMPTY_COUNT_OF_GIFT_CERTIFICATE) != EMPTY_COUNT_OF_GIFT_CERTIFICATE;
    }
}
