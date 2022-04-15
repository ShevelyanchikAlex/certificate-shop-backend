package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tag VALUES(0, ?)";
    private static final String FIND_BY_ID_TAG_QUERY = "SELECT * FROM tag WHERE id=?";
    private static final String FIND_ALL_TAGS_QUERY = "SELECT * FROM tag";
    private static final String FIND_BY_NAME_TAG_QUERY = "SELECT * FROM tag WHERE name=?";
    private static final String UPDATE_TAG_QUERY = "UPDATE tag SET name=? WHERE id=?";
    private static final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id=?";

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
        return jdbcTemplate.queryForObject(FIND_BY_ID_TAG_QUERY, new TagMapper(), id);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_QUERY, new TagMapper());
    }

    @Override
    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME_TAG_QUERY, new TagMapper(), name);
    }

    @Override
    public int update(Tag tag) {
        return jdbcTemplate.update(UPDATE_TAG_QUERY, tag.getName(), tag.getId());
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }
}
