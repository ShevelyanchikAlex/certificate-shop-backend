package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Predicate;

@Component
public class GiftCertificateValidator implements Validator<GiftCertificateDto> {
    private static final String GIFT_CERTIFICATE_NAME_REGEX_PATTERN = "^([A-Za-z ]{1,45})$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN = "^([A-Za-z ]{1,200})$";
    public static final int MIN_VALUE = 0;

    @Override
    public boolean validate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            return false;
        }
        return validateName(giftCertificateDto.getName()) &&
                validateDescription(giftCertificateDto.getDescription()) &&
                validatePrice(giftCertificateDto.getPrice()) &&
                validateDuration(giftCertificateDto.getDuration()) &&
                validateTags(giftCertificateDto.getTagSet());
    }

    public boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Predicate<String> gftCertificateNamePredicate = str -> str.matches(GIFT_CERTIFICATE_NAME_REGEX_PATTERN);
        return gftCertificateNamePredicate.test(name);
    }

    public boolean validateDescription(String description) {
        if (description == null) {
            return false;
        }
        Predicate<String> giftCertificateDescriptionPredicate = str -> str.matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN);
        return giftCertificateDescriptionPredicate.test(description);
    }

    public boolean validatePrice(Integer price) {
        if (price == null) {
            return false;
        }
        Predicate<Integer> giftCertificatePricePredicate = num -> num >= MIN_VALUE;
        return giftCertificatePricePredicate.test(price);
    }

    public boolean validateDuration(Integer duration) {
        if (duration == null) {
            return false;
        }
        Predicate<Integer> giftCertificateDurationPredicate = num -> num >= MIN_VALUE;
        return giftCertificateDurationPredicate.test(duration);
    }

    public boolean validateTags(Set<TagDto> tags) {
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
