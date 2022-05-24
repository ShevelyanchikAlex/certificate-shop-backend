package com.epam.esm.repository.impl;

import com.epam.esm.domain.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String FIND_ALL_USERS_QUERY = "SELECT user FROM User user";
    private static final String COUNT_ALL_USERS_QUERY = "SELECT COUNT(user) FROM User user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User entity) {
        throw new RepositoryException("operation.not.supported", "SAVE");
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll(Integer pageIndex, Integer size) {
        return entityManager.createQuery(FIND_ALL_USERS_QUERY, User.class)
                .setFirstResult((pageIndex - 1) * size)
                .setMaxResults(size).getResultList();
    }

    @Override
    public User update(User entity) {
        throw new RepositoryException("operation.not.supported", "UPDATE");
    }

    @Override
    public void delete(Long id) {
        throw new RepositoryException("operation.not.supported", "DELETE");
    }

    @Override
    public int countAll() {
        return entityManager.createQuery(COUNT_ALL_USERS_QUERY, Long.class).getSingleResult().intValue();
    }
}
