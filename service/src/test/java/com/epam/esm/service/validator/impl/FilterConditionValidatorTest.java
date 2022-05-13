package com.epam.esm.service.validator.impl;

import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        giftCertificateFilterCondition.setName("43243dsada");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertFalse(actual);
    }

    @Test
    void testValidName() {
        //given
        giftCertificateFilterCondition.setName("name");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }

    @Test
    void testInvalidTagName() {
        //given
        giftCertificateFilterCondition.setTagName("4fdss");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertFalse(actual);
    }

    @Test
    void testValidTagName() {
        //given
        giftCertificateFilterCondition.setTagName("#tagName");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }

    @Test
    void testInvalidDescription() {
        //given
        giftCertificateFilterCondition.setDescription("Desc_$#@");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertFalse(actual);
    }

    @Test
    void testValidDescription() {
        //given
        giftCertificateFilterCondition.setDescription("Description of Certificate");
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }

    @Test
    void testValidSortDirection() {
        //given
        giftCertificateFilterCondition.setSortDirection(SortDirection.valueOf("ASC"));
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }

    @Test
    void testValidSortByName() {
        //given
        giftCertificateFilterCondition.setSortByName(true);
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }

    @Test
    void testValidSortByDate() {
        //given
        giftCertificateFilterCondition.setSortByDate(true);
        //when
        boolean actual = filterConditionValidator.validate(giftCertificateFilterCondition);
        //then
        assertTrue(actual);
    }
}