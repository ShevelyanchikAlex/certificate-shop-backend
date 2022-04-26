package com.epam.esm.service.exception;

public final class ServiceErrorCode {
    public static final String REQUEST_VALIDATE_ERROR = "40000";
    public static final String GIFT_CERTIFICATE_VALIDATE_ERROR = "40001";
    public static final String TAG_VALIDATE_ERROR = "40002";
    public static final String FILTER_CONDITION_VALIDATE_ERROR = "40003";
    public static final String UPDATE_CONDITION_VALIDATE_ERROR = "40004";
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "40401";
    public static final String RESOURCE_ALREADY_EXIST = "40901";

    private ServiceErrorCode() {
    }
}
