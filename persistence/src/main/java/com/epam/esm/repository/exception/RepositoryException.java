package com.epam.esm.repository.exception;

import lombok.Getter;

@Getter
public class RepositoryException extends RuntimeException {
    private final Object[] args;

    public RepositoryException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public RepositoryException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args = args;
    }
}
