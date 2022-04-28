package com.epam.esm.service.validator.impl;

import com.epam.esm.repository.filter.condition.FilterCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterConditionValidatorTest {
    private FilterConditionValidator filterConditionValidator;
    private FilterCondition filterCondition;

    @BeforeEach
    public void setUp() {
        filterConditionValidator = new FilterConditionValidator();
        filterCondition = new FilterCondition();
    }

    @Test
    void testInvalidName() {
        filterCondition.setName("43243dsada");
        assertFalse(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidName() {
        filterCondition.setName("name");
        assertTrue(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testInvalidTagName() {
        filterCondition.setTagName("4fdss");
        assertFalse(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidTagName() {
        filterCondition.setTagName("#tagName");
        assertTrue(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testInvalidDescription() {
        filterCondition.setDescription("Desc_$#@");
        assertFalse(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidDescription() {
        filterCondition.setDescription("Description of Certificate");
        assertTrue(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testInvalidSortDirection() {
        filterCondition.setSortDirection("start");
        assertFalse(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidSortDirection() {
        filterCondition.setSortDirection("DESC");
        assertTrue(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidSortByName() {
        filterCondition.setSortByName(true);
        assertTrue(filterConditionValidator.validate(filterCondition));
    }

    @Test
    void testValidSortByDate() {
        filterCondition.setSortByDate(true);
        assertTrue(filterConditionValidator.validate(filterCondition));
    }
}