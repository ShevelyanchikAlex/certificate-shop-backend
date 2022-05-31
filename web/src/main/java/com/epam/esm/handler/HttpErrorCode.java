package com.epam.esm.handler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpErrorCode {
    public static final String NOT_FOUND_CODE = "404";
    public static final String FORBIDDEN_CODE = "403";
    public static final String BAD_REQUEST_CODE = "40000";
    public static final String OPERATION_NOT_SUPPORTED_CODE = "40301";
    public static final String GIFT_CERTIFICATE_NOT_FOUND_CODE = "40401";
    public static final String TAG_NOT_FOUND_CODE = "40402";
    public static final String ORDER_NOT_FOUND_CODE = "40403";
    public static final String USER_NOT_FOUND_CODE = "40404";
    public static final String RESOURCE_ALREADY_EXIST_CODE = "40901";
    public static final String REQUEST_VALIDATE_ERROR_CODE = "40000";
    public static final String GIFT_CERTIFICATE_VALIDATE_ERROR_CODE = "40001";
    public static final String TAG_VALIDATE_ERROR_CODE = "40002";
    public static final String FILTER_CONDITION_VALIDATE_ERROR_CODE = "40003";
    public static final String UPDATE_CONDITION_VALIDATE_ERROR_CODE = "40004";
}
