package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.exception.RepositoryErrorCode;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tag(name) VALUES(?)";
    private static final String FIND_BY_ID_TAG_QUERY = "SELECT * FROM tag WHERE id=?";
    private static final String FIND_BY_NAME_TAG_QUERY = "SELECT * FROM tag WHERE name=?";
    private static final String FIND_ALL_TAGS_QUERY = "SELECT * FROM tag";
    private static final String COUNT_ALL_TAG_QUERY = "SELECT COUNT(*) FROM tag";
    private static final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id=?";
    private static final String FIND_BY_GIFT_CERTIFICATE_ID_QUERY = """
            SELECT * FROM tag
                JOIN gift_certificate_has_tag ON gift_certificate_has_tag.tag_id = tag.id
                JOIN gift_certificate ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id
                WHERE gift_certificate.id=?
            """;
    private static final int SUCCESS_CHANGED_ROW_COUNT = 1;


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Tag tag) {
        return jdbcTemplate.update(INSERT_TAG_QUERY, tag.getName());
    }

    @Override
    public Tag findById(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_TAG_QUERY, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RepositoryException(RepositoryErrorCode.TAG_NOT_FOUND, id);
        }
    }

    @Override
    public Tag findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_NAME_TAG_QUERY, new TagMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            throw new RepositoryException(RepositoryErrorCode.TAG_NOT_FOUND, name);
        }
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_QUERY, new TagMapper());
    }

    @Override
    public Set<Tag> findAllByGiftCertificateId(long giftCertificateId) {
        return new HashSet<>(jdbcTemplate.query(FIND_BY_GIFT_CERTIFICATE_ID_QUERY, new TagMapper(), giftCertificateId));
    }

    @Override
    public int update(Tag tag) {
        throw new RepositoryException(RepositoryErrorCode.OPERATION_NOT_SUPPORTED, "UPDATE");
    }

    @Override
    public int delete(long id) {
        int deletedRowCount = jdbcTemplate.update(DELETE_TAG_QUERY, id);
        if (deletedRowCount != SUCCESS_CHANGED_ROW_COUNT) {
            throw new RepositoryException(RepositoryErrorCode.TAG_NOT_FOUND, id);
        }
        return deletedRowCount;
    }

    @Override
    public int countAll() {
        Integer countOfTags = jdbcTemplate.queryForObject(COUNT_ALL_TAG_QUERY, Integer.class);
        return countOfTags == null ? 0 : countOfTags;
    }
}
