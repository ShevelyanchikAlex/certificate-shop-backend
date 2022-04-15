package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    List<GiftCertificate> findByPartName(String partName);

    List<GiftCertificate> findByPartDescription(String partDescription);

    List<GiftCertificate> sortByDateASC();

    List<GiftCertificate> sortByDateDESC();

    List<GiftCertificate> sortByNameASC();

    List<GiftCertificate> sortByNameDESC();

    List<GiftCertificate> bothSort();
}
