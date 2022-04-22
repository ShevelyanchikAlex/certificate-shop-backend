package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.FilterCondition;

import java.util.List;

public interface GiftCertificateService {
    long save(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findById(long id);

    List<GiftCertificateDto> findAll();

    List<GiftCertificateDto> findWithFilter(FilterCondition filterCondition);

    int update(GiftCertificateDto giftCertificateDto);

    int delete(long id);
}
