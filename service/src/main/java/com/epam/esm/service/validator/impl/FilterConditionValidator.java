package com.epam.esm.service.validator.impl;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Implemented {@link Validator} for {@link GiftCertificateFilterCondition}
 */
@Component
public class FilterConditionValidator implements Validator<GiftCertificateFilterCondition> {
    private static final String ORDER_REGEX_PATTERN = "(ASC)|(DESC)";

    @Override
    public boolean validate(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        if (giftCertificateFilterCondition == null) {
            return false;
        }
        return validateTagName(giftCertificateFilterCondition.getTagName()) &&
                validatePartOfName(giftCertificateFilterCondition.getName()) &&
                validatePartOfDescription(giftCertificateFilterCondition.getDescription()) &&
                validateOrder(giftCertificateFilterCondition.getSortDirection());
    }

    private boolean validateTagName(String tagName) {
        if (tagName == null) {
            return true;
        }
        Predicate<String> tagNamePredicate = str -> str.matches(ServiceConfig.TAG_NAME_REGEX_PATTERN);
        return tagNamePredicate.test(tagName);
    }

    private boolean validatePartOfName(String partOfName) {
        if (partOfName == null) {
            return true;
        }
        Predicate<String> partOfNamePredicate = str -> str.matches(ServiceConfig.GIFT_CERTIFICATE_NAME_REGEX_PATTERN);
        return partOfNamePredicate.test(partOfName);
    }

    private boolean validatePartOfDescription(String partOfDescription) {
        if (partOfDescription == null) {
            return true;
        }
        Predicate<String> partOfDescriptionPredicate = str -> str.matches(ServiceConfig.GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN);
        return partOfDescriptionPredicate.test(partOfDescription);
    }

    private boolean validateOrder(SortDirection sortDirection) {
        if (sortDirection == null) {
            return true;
        }
        Predicate<String> orderPredicate = str -> str.matches(ORDER_REGEX_PATTERN);
        return orderPredicate.test(sortDirection.getSortDirection());
    }
}
