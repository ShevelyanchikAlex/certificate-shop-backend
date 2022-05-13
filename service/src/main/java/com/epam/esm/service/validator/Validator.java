package com.epam.esm.service.validator;

/**
 * Validator for Entity
 *
 * @param <T> Entity
 */
public interface Validator<T> {
    /**
     * Validate Entity
     *
     * @param entity Entity
     * @return true if valid, otherwise false
     */
    boolean validate(T entity);
}
