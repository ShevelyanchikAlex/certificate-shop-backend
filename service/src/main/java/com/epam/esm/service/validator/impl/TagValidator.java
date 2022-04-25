package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class TagValidator implements Validator<TagDto> {
    private static final String TAG_NAME_REGEX_PATTERN = "^(#[A-Za-z0-9_]{1,20})$";

    @Override
    public boolean validate(TagDto tagDto) {
        if (tagDto == null) {
            return false;
        }
        return validateName(tagDto.getName());
    }

    public boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Predicate<String> tagNamePredicate = str -> str.matches(TAG_NAME_REGEX_PATTERN);
        return tagNamePredicate.test(name);
    }
}
