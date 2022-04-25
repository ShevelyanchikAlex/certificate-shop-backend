package com.epam.esm.repository.exception;

public class RepositoryException extends RuntimeException{
    private final String errorCode;
    private final Object[] args;

    public RepositoryException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
