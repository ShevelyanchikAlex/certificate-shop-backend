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
        assertTrue(idValidator.validate(1L));
    }

    @Test
    void testInvalidId() {
        assertFalse(idValidator.validate(null));
    }
}