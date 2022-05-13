package com.epam.esm.service.validator.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Implemented {@link Validator} for {@link TagDto}
 */
@Component
public class TagValidator implements Validator<TagDto> {
    @Override
    public boolean validate(TagDto tagDto) {
        if (tagDto == null) {
            return false;
        }
        return validateName(tagDto.getName());
    }

    private boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Predicate<String> tagNamePredicate = str -> str.matches(ServiceConfig.TAG_NAME_REGEX_PATTERN);
        return tagNamePredicate.test(name);
    }
}
