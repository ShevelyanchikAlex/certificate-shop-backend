package com.epam.esm.service.validator.impl;

import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdValidatorTest {
    private IdValidator idValidator;

    @BeforeEach
    public void setUp() {
        idValidator = new IdValidator();
    }

    @Test
    void testValidId() {
        //then
        Assertions.assertDoesNotThrow(() -> idValidator.validate(1L));
    }

    @Test
    void testInvalidId() {
        //then
        Assertions.assertThrows(ServiceException.class, () -> idValidator.validate(-10L));
    }
}