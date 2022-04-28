package com.epam.esm.dto.serialization.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Implemented {@link DtoSerializer} for {@link GiftCertificate} entity.
 */
@Component
@Qualifier("giftCertificateDtoSerializer")
public class GiftCertificateSerializer implements DtoSerializer<GiftCertificateDto, GiftCertificate> {
    @Override
    public GiftCertificateDto serializeDtoFromEntity(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate serializeDtoToEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
        return giftCertificate;
    }
}
