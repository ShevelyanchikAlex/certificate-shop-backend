package com.epam.esm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpExceptionResponse {
    private final String errorCode;
    private final String errorMessage;

    public HttpStatus getHttpStatus() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (errorCode.startsWith(HttpErrorCode.NOT_FOUND_CODE)) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (errorCode.startsWith(HttpErrorCode.FORBIDDEN_CODE)) {
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return httpStatus;
    }
}
