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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link GiftCertificateRepository}
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT gift_certificate FROM GiftCertificate gift_certificate";
    private static final String EXIST_GIFT_CERTIFICATES_QUERY = "SELECT COUNT(gift_certificate) FROM GiftCertificate gift_certificate WHERE gift_certificate.name=:giftCertificateName";
    private static final int EMPTY_COUNT_OF_GIFT_CERTIFICATE = 0;

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
    public GiftCertificate findById(long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery(FIND_ALL_GIFT_CERTIFICATES_QUERY, GiftCertificate.class).getResultList();

    }

    @Override
    public List<GiftCertificate> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        queryBuilderResult = filterQueryBuilder.buildQuery(giftCertificateFilterCondition);
        Query query = entityManager.createNativeQuery(queryBuilderResult.getQuery(), GiftCertificate.class);
        Set<Map.Entry<String, String>> entries = queryBuilderResult.getParameters().entrySet();
        for (Map.Entry<String, String> e : entries) {
            query.setParameter(e.getKey(), e.getValue());
        }
        return query.getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        GiftCertificate certificate = Optional.ofNullable(entityManager.find(GiftCertificate.class, id))
                .orElseThrow(() -> new RepositoryException("gift.certificate.not.found", id));
        entityManager.remove(certificate);
    }

    @Override
    public boolean existsGiftCertificateByName(String name) {
        TypedQuery<Long> query = entityManager.createQuery(EXIST_GIFT_CERTIFICATES_QUERY, Long.class);
        query.setParameter("giftCertificateName", name);
        return query.getResultStream().findFirst().orElse(0L) != EMPTY_COUNT_OF_GIFT_CERTIFICATE;
    }
}
