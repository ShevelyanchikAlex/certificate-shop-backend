package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.filter.FilterCondition;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    List<GiftCertificate> findWithFilter(FilterCondition filterCondition);

    int associateGiftCertificateWithTag(long giftCertificateId, long tagId);
}
