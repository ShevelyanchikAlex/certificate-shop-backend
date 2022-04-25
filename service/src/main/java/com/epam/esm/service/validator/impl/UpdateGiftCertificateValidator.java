package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class UpdateGiftCertificateValidator implements Validator<GiftCertificateDto> {
    private static final String GIFT_CERTIFICATE_NAME_REGEX_PATTERN = "^([A-Za-z ]{1,45})$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN = "^([A-Za-z ]{1,200})$";

    @Override
    public boolean validate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            return false;
        }
        return validateName(giftCertificateDto.getName()) &&
                validateDescription(giftCertificateDto.getDescription()) &&
                validatePrice(giftCertificateDto.getPrice()) &&
                validateDuration(giftCertificateDto.getDuration());
    }

    public boolean validateName(String name) {
        if (name == null) {
            return true;
        }
        Predicate<String> gftCertificateNamePredicate = str -> str.matches(GIFT_CERTIFICATE_NAME_REGEX_PATTERN);
        return gftCertificateNamePredicate.test(name);
    }

    public boolean validateDescription(String description) {
        if (description == null) {
            return true;
        }
        Predicate<String> giftCertificateDescriptionPredicate = str -> str.matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN);
        return giftCertificateDescriptionPredicate.test(description);
    }

    public boolean validatePrice(Integer price) {
        if (price == null) {
            return true;
        }
        Predicate<Integer> giftCertificatePricePredicate = num -> num >= 0;
        return giftCertificatePricePredicate.test(price);
    }

    public boolean validateDuration(Integer duration) {
        if (duration == null) {
            return true;
        }
        Predicate<Integer> giftCertificateDurationPredicate = num -> num >= 0;
        return giftCertificateDurationPredicate.test(duration);
    }
}
