package com.epam.esm.dto.converter.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Implemented {@link DtoConverter} for {@link GiftCertificate} entity.
 */
@Component
@Qualifier("giftCertificateDtoConverter")
public class GiftCertificateConverter implements DtoConverter<GiftCertificateDto, GiftCertificate> {
    private final DtoConverter<TagDto, Tag> tagDtoConverter;

    @Autowired
    public GiftCertificateConverter(@Qualifier("tagDtoConverter") DtoConverter<TagDto, Tag> tagDtoConverter) {
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public GiftCertificateDto convertDtoFromEntity(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        giftCertificateDto.setTags(giftCertificate.getTags()
                .stream().map(tagDtoConverter::convertDtoFromEntity).collect(Collectors.toList()));
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate convertDtoToEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
        giftCertificate.setTags(giftCertificateDto.getTags()
                .stream().map(tagDtoConverter::convertDtoToEntity).collect(Collectors.toList()));
        return giftCertificate;
    }
}
