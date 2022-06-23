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
     */
    void validate(T entity);
}
