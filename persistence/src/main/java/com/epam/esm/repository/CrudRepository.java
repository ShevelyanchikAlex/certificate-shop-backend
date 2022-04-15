package com.epam.esm.repository;

import java.util.List;

public interface CrudRepository<T> {
    long save(T entity);

    T findById(long id);

    List<T> findAll();

    int update(T entity);

    int delete(long id);
}
