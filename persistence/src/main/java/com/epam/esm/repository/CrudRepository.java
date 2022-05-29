package com.epam.esm.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link CrudRepository} is an interface that contains all CRUD operations of the API.
 *
 * @param <T> Entity
 */
public interface CrudRepository<T> {
    /**
     * Saves Entity to data source
     *
     * @param entity Entity
     * @return saved Entity
     */
    T save(T entity);

    /**
     * Finds Entity by id
     *
     * @param id id of Entity
     * @return Found Entity
     */
    T findById(Long id);

    /**
     * Finds all Entity
     *
     * @param pageable Pageable
     * @return List of entity
     */
    List<T> findAll(Pageable pageable);

    /**
     * Updates Entity
     *
     * @param entity Entity with changed param
     * @return updated Entity
     */
    T update(T entity);

    /**
     * Deletes Entity by id
     *
     * @param id id of Entity
     */
    void delete(Long id);
}
