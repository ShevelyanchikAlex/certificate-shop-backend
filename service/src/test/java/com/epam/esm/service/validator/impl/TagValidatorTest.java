package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {
    private TagValidator tagValidator;
    private static TagDto tagDto;

    @BeforeEach
    public void setUp() {
        tagValidator = new TagValidator();
        tagDto = new TagDto(1L, "#tag1");
    }

    @Test
    void testValidTagName() {
        tagDto.setName("r23r");
        assertFalse(tagValidator.validate(tagDto));
    }

    @Test
    void testInvalidTagName() {
        assertTrue(tagValidator.validate(tagDto));
    }
}