package com.epam.esm.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final String errorCode;
    private final Object[] args;

    public ServiceException(String errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }
}
