package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.filter.FilterQueryBuilder;
import com.epam.esm.repository.filter.UpdateQueryBuilder;
import com.epam.esm.repository.filter.condition.FilterCondition;
import com.epam.esm.repository.filter.condition.UpdateCondition;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate VALUES(0, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT * FROM gift_certificate";
    private static final String DELETE_GIFT_CERTIFICATE_QUERY = "DELETE FROM gift_certificate WHERE id=?";
    private static final String ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY = "INSERT INTO gift_certificate_has_tag VALUES(?,?)";

    private final JdbcTemplate jdbcTemplate;
    private final FilterQueryBuilder filterBuilder;
    private final UpdateQueryBuilder updateQueryBuilder;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, FilterQueryBuilder filterBuilder, UpdateQueryBuilder updateQueryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.filterBuilder = filterBuilder;
        this.updateQueryBuilder = updateQueryBuilder;
    }

    @Override
    public long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_GIFT_CERTIFICATE_QUERY, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, giftCertificate.getName());
                    ps.setString(2, giftCertificate.getDescription());
                    ps.setInt(3, giftCertificate.getPrice());
                    ps.setInt(4, giftCertificate.getDuration());
                    ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
                    ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public GiftCertificate findById(long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_GIFT_CERTIFICATE_QUERY, new GiftCertificateMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES_QUERY, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> findWithFilter(FilterCondition filterCondition) {
        return jdbcTemplate.query(filterBuilder.buildQuery(filterCondition), new GiftCertificateMapper());
    }

    @Override
    public int update(GiftCertificate giftCertificate) {
        UpdateCondition updateCondition = new UpdateCondition(giftCertificate.getId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration());
        return jdbcTemplate.update(updateQueryBuilder.buildUpdateQuery(updateCondition));
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_QUERY, id);
    }

    @Override
    public int associateGiftCertificateWithTag(long giftCertificateId, long tagId) {
        return jdbcTemplate.update(ASSOCIATE_GIFT_CERTIFICATE_WITH_TAG_QUERY, giftCertificateId, tagId);
    }
}
