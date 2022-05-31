package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
    private static final String FIND_MOST_POPULAR_TAGS_QUERY = """
            SELECT tag.id, tag.name FROM tag
            JOIN gift_certificate_has_tag gcht ON tag.id = gcht.tag_id AND gcht.gift_certificate_id IN
            (SELECT gift_certificate.id FROM gift_certificate
            JOIN order_has_gift_certificate ohgc ON gift_certificate.id = ohgc.gift_certificate_id
            JOIN orders ON gift_certificate.id = ohgc.gift_certificate_id AND orders.user_id = (
            SELECT user.id FROM user
            JOIN orders ON user.id = orders.user_id
            JOIN gift_certificate gc ON gc.id = ohgc.gift_certificate_id
            GROUP BY user.id
            ORDER BY SUM(orders.total_price) DESC LIMIT 1))
            GROUP BY gcht.tag_id
            ORDER BY COUNT(gcht.gift_certificate_id) DESC""";
    private static final long EMPTY_COUNT_OF_TAGS = 0L;
    private static final int ONE_PAGE = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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
    public List<Tag> findAll(Pageable pageable) {
        return entityManager.createQuery(FIND_ALL_TAGS_QUERY, Tag.class)
                .setFirstResult((pageable.getPageNumber() - ONE_PAGE) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
    }

    @Override
    public List<Tag> findMostPopularTags(Pageable pageable) {
        Query query = entityManager.createNativeQuery(FIND_MOST_POPULAR_TAGS_QUERY, Tag.class)
                .setFirstResult((pageable.getPageNumber() - ONE_PAGE) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
        List<Tag> tags = new ArrayList<>();
        query.getResultStream().filter(Tag.class::isInstance).forEach(obj -> tags.add((Tag) obj));
        return tags;
    }

    @Override
    public Tag update(Tag tag) {
        throw new RepositoryException("operation.not.supported", "UPDATE");
    }

    @Override
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
