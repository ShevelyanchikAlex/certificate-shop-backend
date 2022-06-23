package com.epam.esm.service.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidatorRegexPattern {
    public static final String SORT_DIRECTION_REGEX_PATTERN = "(ASC)|(DESC)";
    public static final String TAG_NAME_REGEX_PATTERN = "^(#[A-Za-z0-9_]{1,20})$";
    public static final String GIFT_CERTIFICATE_NAME_REGEX_PATTERN = "^([A-Za-z0-9_ ]{1,45})$";
    public static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX_PATTERN = "^([A-Za-z0-9_ ]{1,200})$";
    public static final String USER_NAME_REGEX_PATTERN = "^([A-Za-z0-9_ ]{1,50})$";
    public static final String EMAIL_REGEX_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX_PATTERN = "^([A-Za-z0-9_]{4,30})$";
}