package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagValidatorTest {
    private TagValidator tagValidator;
    private static TagDto tagDto;

    @BeforeEach
    public void setUp() {
        tagValidator = new TagValidator();
        tagDto = new TagDto(1L, "#tag1$42");
    }

    @Test
    void testValidTagName() {
        //given
        tagDto.setName("#tag_1");
        //then
        Assertions.assertDoesNotThrow(() -> tagValidator.validate(tagDto));
    }

    @Test
    void testInvalidTagName() {
        //then
        Assertions.assertThrows(ServiceException.class, () -> tagValidator.validate(tagDto));

    }
}