package com.epam.esm.repository.exception;

import lombok.Getter;

@Getter
public class RepositoryException extends RuntimeException {
    private final String errorCode;
    private final Object[] args;

    public RepositoryException(String errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }
}
