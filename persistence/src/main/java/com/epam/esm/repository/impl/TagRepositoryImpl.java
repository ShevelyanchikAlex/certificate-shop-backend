package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TagRepository}
 */
@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String FIND_BY_TAG_NAME_QUERY = "SELECT tag FROM Tag tag WHERE tag.name=:tagName";
    private static final String FIND_ALL_TAGS_QUERY = "SELECT tag FROM Tag tag";
    private static final String EXIST_TAG_BY_NAME_QUERY = "SELECT COUNT(tag) FROM Tag tag WHERE tag.name=:tagName";
    private static final String COUNT_ALL_TAG_QUERY = "SELECT COUNT(tag) FROM Tag tag";
    private static final long EMPTY_COUNT_OF_TAGS = 0L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(FIND_BY_TAG_NAME_QUERY, Tag.class);
        query.setParameter("tagName", name);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public boolean existsTagByName(String name) {
        TypedQuery<Long> query = entityManager.createQuery(EXIST_TAG_BY_NAME_QUERY, Long.class);
        query.setParameter("tagName", name);
        return query.getResultStream().findFirst().orElse(EMPTY_COUNT_OF_TAGS) != EMPTY_COUNT_OF_TAGS;
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(FIND_ALL_TAGS_QUERY, Tag.class).getResultList();
    }

    @Override
    public Tag update(Tag tag) {
        throw new RepositoryException("operation.not.supported", "UPDATE");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = Optional.ofNullable(entityManager.find(Tag.class, id))
                .orElseThrow(() -> new RepositoryException("tag.not.found", id));
        entityManager.remove(tag);
    }

    @Override
    public int countAll() {
        return entityManager.createQuery(COUNT_ALL_TAG_QUERY, Long.class).getSingleResult().intValue();
    }
}
