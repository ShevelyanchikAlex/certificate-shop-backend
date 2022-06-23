package com.epam.esm.repository;

import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link GiftCertificateFilterRepository} interface for filtered find {@link com.epam.esm.domain.GiftCertificate}
 */
public interface GiftCertificateFilterRepository {
    /**
     * Finds GiftCertificates by FilterCondition
     *
     * @param pageable                       Pageable
     * @param giftCertificateFilterCondition Condition for Filtering
     * @return List of GiftCertificates
     */
    List<Object> findWithFilter(Pageable pageable, GiftCertificateFilterCondition giftCertificateFilterCondition);
}
