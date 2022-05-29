package com.epam.esm.repository.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final String FIND_ALL_ORDERS_QUERY = "SELECT o FROM Order o";
    private static final String COUNT_ALL_ORDERS_QUERY = "SELECT COUNT(o) FROM Order o";
    private static final int ONE_PAGE = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findAll(Pageable pageable) {
        return entityManager.createQuery(FIND_ALL_ORDERS_QUERY, Order.class)
                .setFirstResult((pageable.getPageNumber() - ONE_PAGE) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
    }

    @Override
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = Optional.ofNullable(entityManager.find(Order.class, id))
                .orElseThrow(() -> new RepositoryException("order.not.found", id));
        entityManager.remove(order);
    }

    @Override
    public int countAll() {
        return entityManager.createQuery(COUNT_ALL_ORDERS_QUERY, Long.class).getSingleResult().intValue();
    }
}
