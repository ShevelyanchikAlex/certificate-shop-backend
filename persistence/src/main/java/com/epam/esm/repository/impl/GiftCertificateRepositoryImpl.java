package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.exception.RepositoryErrorCode;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.filter.FilterQueryBuilder;
import com.epam.esm.repository.filter.QueryBuilderResult;
import com.epam.esm.repository.filter.UpdateQueryBuilder;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.GiftCertificateUpdateCondition;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link GiftCertificateRepository}
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate VALUES(0, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT * FROM gift_certificate";
    private static final String EXIST_GIFT_CERTIFICATES_QUERY = "SELECT COUNT(*) FROM gift_certificate WHERE name=?";
    private static final String DELETE_GIFT_CERTIFICATE_QUERY = "DELETE FROM gift_certificate WHERE id=?";
    private static final String DE_ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_has_tag.gift_certificate_id=? AND gift_certificate_has_tag.tag_id=?";
    private static final String ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY = "INSERT INTO gift_certificate_has_tag VALUES(?,?)";
    private static final String ID_COLUMN = "id";
    private static final int SUCCESS_CHANGED_ROW_COUNT = 1;
    private static final int EMPTY_COUNT_OF_GIFT_CERTIFICATE = 0;

    private final JdbcTemplate jdbcTemplate;
    private final FilterQueryBuilder filterQueryBuilder;
    private final UpdateQueryBuilder updateQueryBuilder;
    private QueryBuilderResult queryBuilderResult;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, FilterQueryBuilder filterQueryBuilder,
                                         UpdateQueryBuilder updateQueryBuilder, QueryBuilderResult queryBuilderResult) {
        this.jdbcTemplate = jdbcTemplate;
        this.filterQueryBuilder = filterQueryBuilder;
        this.updateQueryBuilder = updateQueryBuilder;
        this.queryBuilderResult = queryBuilderResult;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE_QUERY, new String[]{ID_COLUMN});
                        ps.setString(1, giftCertificate.getName());
                        ps.setString(2, giftCertificate.getDescription());
                        ps.setInt(3, giftCertificate.getPrice());
                        ps.setInt(4, giftCertificate.getDuration());
                        ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
                        ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
                        return ps;
                    },
                    keyHolder);
            giftCertificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return giftCertificate;
        } catch (DuplicateKeyException e) {
            throw new RepositoryException(e.getMessage(), RepositoryErrorCode.RESOURCE_ALREADY_EXIST, giftCertificate.getName());
        }
    }

    @Override
    public GiftCertificate findById(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_GIFT_CERTIFICATE_QUERY, new GiftCertificateMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RepositoryException(e.getMessage(), RepositoryErrorCode.GIFT_CERTIFICATE_NOT_FOUND, id);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES_QUERY, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        queryBuilderResult = filterQueryBuilder.buildQuery(giftCertificateFilterCondition);
        return jdbcTemplate.query(queryBuilderResult.getQuery(), new GiftCertificateMapper(), queryBuilderResult.getParameters().toArray());
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificateUpdateCondition updateCondition = new GiftCertificateUpdateCondition(giftCertificate.getId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration());
        queryBuilderResult = updateQueryBuilder.buildUpdateQuery(updateCondition);
        jdbcTemplate.update(queryBuilderResult.getQuery(), queryBuilderResult.getParameters().toArray());
        return giftCertificate;
    }

    @Override
    public void delete(long id) {
        int deletedRowCount = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_QUERY, id);
        if (deletedRowCount != SUCCESS_CHANGED_ROW_COUNT) {
            throw new RepositoryException("Gift Certificate not found in time performing a delete operation", RepositoryErrorCode.GIFT_CERTIFICATE_NOT_FOUND, id);
        }
    }

    @Override
    public void associateGiftCertificateWithTag(long giftCertificateId, long tagId) {
        jdbcTemplate.update(ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY, giftCertificateId, tagId);
    }

    @Override
    public void deAssociateGiftCertificateWithTag(long giftCertificateId, long tagId) {
        jdbcTemplate.update(DE_ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY, giftCertificateId, tagId);
    }

    @Override
    public boolean existsGiftCertificateByName(String name) {
        Integer countOfGiftCertificate = jdbcTemplate.queryForObject(EXIST_GIFT_CERTIFICATES_QUERY, Integer.class, name);
        return (countOfGiftCertificate != null) && (countOfGiftCertificate != EMPTY_COUNT_OF_GIFT_CERTIFICATE);
    }
}
