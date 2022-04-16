package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    long save(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findById(long id);

    List<GiftCertificateDto> findAll();

    List<GiftCertificateDto> findByPartName(String partName);

    List<GiftCertificateDto> findByPartDescription(String partDescription);

    int delete(long id);
}
