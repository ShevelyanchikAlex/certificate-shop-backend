package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link GiftCertificateRepository} is an interface that contains all operations available for {@link GiftCertificate} of the API.
 */
public interface GiftCertificateRepository extends CrudRepository<GiftCertificate>, CounterRepository {
    /**
     * Finds GiftCertificates by FilterCondition
     *
     * @param pageable                       Pageable
     * @param giftCertificateFilterCondition Condition for Filtering
     * @return List of GiftCertificates
     */
    List<GiftCertificate> findWithFilter(Pageable pageable, GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Checks if there is an GiftCertificate with name
     *
     * @param name Name of GiftCertificate
     * @return true if exists, otherwise false
     */
    boolean existsGiftCertificateByName(String name);
}
