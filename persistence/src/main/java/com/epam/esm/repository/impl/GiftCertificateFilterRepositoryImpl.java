package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateFilterRepository;
import com.epam.esm.repository.filter.FilterQueryBuilder;
import com.epam.esm.repository.filter.QueryBuilderResult;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GiftCertificateFilterRepositoryImpl implements GiftCertificateFilterRepository {
    private static final int ONE_PAGE = 1;

    @PersistenceContext
    private EntityManager entityManager;

    private final FilterQueryBuilder filterQueryBuilder;
    private QueryBuilderResult queryBuilderResult;

    public GiftCertificateFilterRepositoryImpl(FilterQueryBuilder filterQueryBuilder, QueryBuilderResult queryBuilderResult) {
        this.filterQueryBuilder = filterQueryBuilder;
        this.queryBuilderResult = queryBuilderResult;
    }

    @Override
    public List<Object> findWithFilter(Pageable pageable, GiftCertificateFilterCondition giftCertificateFilterCondition) {
        queryBuilderResult = filterQueryBuilder.buildQuery(giftCertificateFilterCondition);
        Query query = entityManager.createNativeQuery(queryBuilderResult.getQuery());
        Set<Map.Entry<String, String>> entries = queryBuilderResult.getParameters().entrySet();
        for (Map.Entry<String, String> e : entries) {
            query.setParameter(e.getKey(), e.getValue());
        }
        return query.setFirstResult((pageable.getPageNumber() - ONE_PAGE) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
    }
}
