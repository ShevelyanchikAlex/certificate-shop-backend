package com.epam.esm.service.validator.impl;

import com.epam.esm.repository.filter.condition.FilterCondition;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class FilterConditionValidator implements Validator<FilterCondition> {
    private static final String TAG_NAME_REGEX_PATTERN = "^(#[A-Za-z0-9_]{1,20})$";
    private static final String GIFT_CERTIFICATE_NAME_REGEX_PATTERN = "^([A-Za-z ]{1,45})$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN = "^([A-Za-z ]{1,200})$";
    private static final String ORDER_REGEX_PATTERN = "(ASC)|(DESC)";

    @Override
    public boolean validate(FilterCondition filterCondition) {
        if (filterCondition == null) {
            return false;
        }
        return validateTagName(filterCondition.getTagName()) &&
                validatePartOfName(filterCondition.getName()) &&
                validatePartOfDescription(filterCondition.getDescription()) &&
                validateOrder(filterCondition.getSortDirection());
    }

    public boolean validateTagName(String tagName) {
        if (tagName == null) {
            return true;
        }
        Predicate<String> tagNamePredicate = str -> str.matches(TAG_NAME_REGEX_PATTERN);
        return tagNamePredicate.test(tagName);
    }

    public boolean validatePartOfName(String partOfName) {
        if (partOfName == null) {
            return true;
        }
        Predicate<String> partOfNamePredicate = str -> str.matches(GIFT_CERTIFICATE_NAME_REGEX_PATTERN);
        return partOfNamePredicate.test(partOfName);
    }

    public boolean validatePartOfDescription(String partOfDescription) {
        if (partOfDescription == null) {
            return true;
        }
        Predicate<String> partOfDescriptionPredicate = str -> str.matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN);
        return partOfDescriptionPredicate.test(partOfDescription);
    }

    public boolean validateOrder(String order) {
        if (order == null) {
            return true;
        }
        Predicate<String> orderPredicate = str -> str.matches(ORDER_REGEX_PATTERN);
        return orderPredicate.test(order);
    }
}
