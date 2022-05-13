package com.epam.esm.service.validator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdValidatorTest {
    private IdValidator idValidator;

    @BeforeEach
    public void setUp() {
        idValidator = new IdValidator();
    }

    @Test
    void testValidId() {
        //when
        boolean actual = idValidator.validate(1L);
        //then
        assertTrue(actual);
    }

    @Test
    void testInvalidId() {
        //when
        boolean actual = idValidator.validate(null);
        //then
        assertFalse(actual);
    }
}