package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, GiftCertificateFilterRepository {
    /**
     * Checks if there is an GiftCertificate with name
     *
     * @param name Name of GiftCertificate
     * @return true if exists, otherwise false
     */
    boolean existsGiftCertificateByName(String name);
}
