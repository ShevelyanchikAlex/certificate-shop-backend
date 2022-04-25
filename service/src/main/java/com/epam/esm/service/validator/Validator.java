package com.epam.esm.service.validator;

public interface Validator<T> {
    boolean validate(T entity);
}
