package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

class GiftCertificateValidatorTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);
    private GiftCertificateValidator giftCertificateValidator;
    private GiftCertificateDto giftCertificateDto;

    @BeforeEach
    public void setUp() {
        giftCertificateValidator = new GiftCertificateValidator();
        giftCertificateDto = new GiftCertificateDto(3L, "Gift Certificate third",
                "DescriptionUpd third", new BigDecimal(20), 2,
                DATE_TIME, DATE_TIME, List.of(new TagDto(1L, "#tag1"),
                new TagDto(2L, "#tag2")));
    }

    @Test
    void testValidGiftCertificateDto() {
        //when
        Assertions.assertDoesNotThrow(() -> giftCertificateValidator.validate(giftCertificateDto));
    }

    @Test
    void testInvalidGiftCertificateDto() {
        //given
        giftCertificateDto.setDescription("()*&ddd");
        giftCertificateDto.setName("()*&ss");
        //then
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateValidator.validate(giftCertificateDto));
    }
}