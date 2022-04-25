package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class HttpExceptionResponse {
    private static final String NOT_FOUND_STATUS = "404";
    private static final String FORBIDDEN_STATUS = "403";

    private final String errorCode;
    private final String errorMessage;

    public HttpExceptionResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (errorCode.startsWith(NOT_FOUND_STATUS)) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (errorCode.startsWith(FORBIDDEN_STATUS)) {
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return httpStatus;
    }
}
