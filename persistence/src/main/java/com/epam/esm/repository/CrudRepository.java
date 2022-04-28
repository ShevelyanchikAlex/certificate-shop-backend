package com.epam.esm.repository;

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
     * @return id of saved Entity
     */
    long save(T entity);

    /**
     * Finds Entity by id
     *
     * @param id id of Entity
     * @return Found Entity
     */
    T findById(long id);

    /**
     * Finds all Entity
     *
     * @return List of entity
     */
    List<T> findAll();

    /**
     * Updates Entity
     *
     * @param entity Entity with changed param
     * @return 1 if the update operation was successful, otherwise 0
     */
    int update(T entity);

    /**
     * Deletes Entity by id
     *
     * @param id id of Entity
     * @return 1 if the update operation was successful, otherwise 0
     */
    int delete(long id);
}
