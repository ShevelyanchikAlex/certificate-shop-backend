package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate VALUES(0, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT * FROM gift_certificate";
    private static final String FIND_BY_NAME_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificate WHERE name=?";
    private static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE gift_certificate SET name=?, description=?, price=?," +
            " duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String DELETE_GIFT_CERTIFICATE_QUERY = "DELETE FROM gift_certificate WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(INSERT_GIFT_CERTIFICATE_QUERY, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate());
    }

    @Override
    public GiftCertificate findById(long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_GIFT_CERTIFICATE_QUERY, new GiftCertificateMapper(), id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES_QUERY, new GiftCertificateMapper());
    }

    @Override
    public int update(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_QUERY, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate());
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_QUERY, id);
    }

    @Override
    public List<GiftCertificate> findByPartName(String partName) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByPartDescription(String partDescription) {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByDateASC() {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByDateDESC() {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByNameASC() {
        return null;
    }

    @Override
    public List<GiftCertificate> sortByNameDESC() {
        return null;
    }

    @Override
    public List<GiftCertificate> bothSort() {
        return null;
    }
}
