package com.epam.esm.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final Object[] args;

    public ServiceException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public ServiceException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args = args;
    }
}
