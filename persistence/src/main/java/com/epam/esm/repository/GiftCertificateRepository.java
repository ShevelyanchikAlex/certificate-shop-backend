package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;

import java.util.List;

/**
 * {@link GiftCertificateRepository} is an interface that contains all operations available for {@link GiftCertificate} of the API.
 */
public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    /**
     * Finds all certificates by filter
     *
     * @param giftCertificateFilterCondition Condition for filtering
     * @return List with filtered GiftCertificates
     */
    List<GiftCertificate> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Checks if there is an GiftCertificate with name
     *
     * @param name Name of GiftCertificate
     * @return true if exists, otherwise false
     */
    boolean existsGiftCertificateByName(String name);
}
