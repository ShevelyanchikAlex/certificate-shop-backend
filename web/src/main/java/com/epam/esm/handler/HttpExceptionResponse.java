package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpExceptionResponse {
    private static final String NOT_FOUND_STATUS = "404";
    private static final String FORBIDDEN_STATUS = "403";

    private final String errorCode;
    private final String errorMessage;

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
