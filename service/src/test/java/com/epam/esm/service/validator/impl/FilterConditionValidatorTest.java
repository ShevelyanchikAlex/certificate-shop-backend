package com.epam.esm.service.validator.impl;

import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterConditionValidatorTest {
    private FilterConditionValidator filterConditionValidator;
    private GiftCertificateFilterCondition giftCertificateFilterCondition;

    @BeforeEach
    public void setUp() {
        filterConditionValidator = new FilterConditionValidator();
        giftCertificateFilterCondition = new GiftCertificateFilterCondition();
    }

    @Test
    void testInvalidName() {
        //given
        giftCertificateFilterCondition.setName("43243dsada@#");
        //then
        Assertions.assertThrows(ServiceException.class, () -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testValidName() {
        //given
        giftCertificateFilterCondition.setName("name");
        //then
        Assertions.assertDoesNotThrow(() -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testInvalidDescription() {
        //given
        giftCertificateFilterCondition.setDescription("Desc$#@");
        //then
        Assertions.assertThrows(ServiceException.class, () -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testValidDescription() {
        //given
        giftCertificateFilterCondition.setDescription("Description of Certificate");
        //then
        Assertions.assertDoesNotThrow(() -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testValidSortDirection() {
        //given
        giftCertificateFilterCondition.setSortDirection(SortDirection.valueOf("ASC"));
        //then
        Assertions.assertDoesNotThrow(() -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testValidSortByName() {
        //given
        giftCertificateFilterCondition.setSortByName(true);
        //then
        Assertions.assertDoesNotThrow(() -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }

    @Test
    void testValidSortByDate() {
        //given
        giftCertificateFilterCondition.setSortByDate(true);
        //then
        Assertions.assertDoesNotThrow(() -> filterConditionValidator.validate(giftCertificateFilterCondition));
    }
}