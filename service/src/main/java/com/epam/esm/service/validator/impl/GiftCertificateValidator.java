package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.validator.Validator;
import com.epam.esm.service.validator.ValidatorRegexPattern;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

/**
 * Implemented {@link Validator} for {@link GiftCertificateDto}
 */
@Component
public class GiftCertificateValidator implements Validator<GiftCertificateDto> {
    private static final int MIN_VALUE = 0;

    @Override
    public boolean validate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            return false;
        }
        return validateName(giftCertificateDto.getName()) &&
                validateDescription(giftCertificateDto.getDescription()) &&
                validatePrice(giftCertificateDto.getPrice()) &&
                validateDuration(giftCertificateDto.getDuration()) &&
                validateTags(giftCertificateDto.getTags());
    }

    private boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Predicate<String> gftCertificateNamePredicate = str -> str.matches(ValidatorRegexPattern.GIFT_CERTIFICATE_NAME_REGEX_PATTERN);
        return gftCertificateNamePredicate.test(name);
    }

    private boolean validateDescription(String description) {
        if (description == null) {
            return false;
        }
        Predicate<String> giftCertificateDescriptionPredicate = str -> str.matches(ValidatorRegexPattern.GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN);
        return giftCertificateDescriptionPredicate.test(description);
    }

    private boolean validatePrice(BigDecimal price) {
        if (price == null) {
            return false;
        }
        Predicate<BigDecimal> giftCertificatePricePredicate = num -> num.compareTo(BigDecimal.ZERO) >= MIN_VALUE;
        return giftCertificatePricePredicate.test(price);
    }

    private boolean validateDuration(Integer duration) {
        if (duration == null) {
            return false;
        }
        Predicate<Integer> giftCertificateDurationPredicate = num -> num >= MIN_VALUE;
        return giftCertificateDurationPredicate.test(duration);
    }

    private boolean validateTags(List<TagDto> tags) {
        if (tags == null) {
            return true;
        }
        TagValidator tagValidator = new TagValidator();
        for (TagDto tagDto : tags) {
            if (!tagValidator.validate(tagDto)) {
                return false;
            }
        }
        return true;
    }
}
