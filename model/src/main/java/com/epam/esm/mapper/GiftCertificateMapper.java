package com.epam.esm.mapper;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface GiftCertificateMapper {
    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);
}
