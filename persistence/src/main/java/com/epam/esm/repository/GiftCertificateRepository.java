package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;

import java.util.List;

/**
 * {@link GiftCertificateRepository} is an interface that contains all operations available for {@link GiftCertificate} of the API.
 */
public interface GiftCertificateRepository extends CrudRepository<GiftCertificate>, CounterRepository {
    /**
     * Finds GiftCertificates by FilterCondition
     *
     * @param pageIndex                      Number of Page
     * @param size                           Size of Page
     * @param giftCertificateFilterCondition Condition for Filtering
     * @return List of GiftCertificates
     */
    List<GiftCertificate> findWithFilter(Integer pageIndex, Integer size, GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Checks if there is an GiftCertificate with name
     *
     * @param name Name of GiftCertificate
     * @return true if exists, otherwise false
     */
    boolean existsGiftCertificateByName(String name);
}
