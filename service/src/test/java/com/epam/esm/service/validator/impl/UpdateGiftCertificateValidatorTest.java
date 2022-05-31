package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateGiftCertificateValidatorTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);
    private UpdateGiftCertificateValidator updateGiftCertificateValidator;
    private GiftCertificateDto giftCertificateDto;

    @BeforeEach
    public void setUp() {
        updateGiftCertificateValidator = new UpdateGiftCertificateValidator();
        giftCertificateDto = new GiftCertificateDto(3L, "Gift Certificate third",
                "DescriptionUpd third", new BigDecimal(20), 2,
                DATE_TIME, DATE_TIME, List.of(new TagDto(1L, "#tag1"),
                new TagDto(2L, "#tag2")));
    }

    @Test
    void testValidGiftCertificateDto() {
        //when
        boolean actual = updateGiftCertificateValidator.validate(giftCertificateDto);
        //then
        assertTrue(actual);
    }

    @Test
    void testInvalidGiftCertificateDto() {
        //given
        giftCertificateDto.setDescription("()*&ddd");
        giftCertificateDto.setName("()*&ss");
        //when
        boolean actual = updateGiftCertificateValidator.validate(giftCertificateDto);
        //then
        assertFalse(actual);
    }
}