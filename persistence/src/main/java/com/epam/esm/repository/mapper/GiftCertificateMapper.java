package com.epam.esm.repository.mapper;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final String GIFT_CERTIFICATE_ID_COLUMN = "id";
    private static final String GIFT_CERTIFICATE_NAME_COLUMN = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_COLUMN = "description";
    private static final String GIFT_CERTIFICATE_PRICE_COLUMN = "price";
    private static final String GIFT_CERTIFICATE_DURATION_COLUMN = "duration";
    private static final String GIFT_CERTIFICATE_CREATE_DATE_COLUMN = "create_date";
    private static final String GIFT_CERTIFICATE_LAST_UPDATE_DATE_COLUMN = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(GIFT_CERTIFICATE_ID_COLUMN));
        giftCertificate.setName(resultSet.getString(GIFT_CERTIFICATE_NAME_COLUMN));
        giftCertificate.setDescription(resultSet.getString(GIFT_CERTIFICATE_DESCRIPTION_COLUMN));
        giftCertificate.setPrice(resultSet.getInt(GIFT_CERTIFICATE_PRICE_COLUMN));
        giftCertificate.setDuration(resultSet.getInt(GIFT_CERTIFICATE_DURATION_COLUMN));
        giftCertificate.setCreateDate(resultSet.getTimestamp(GIFT_CERTIFICATE_CREATE_DATE_COLUMN).toLocalDateTime());
        giftCertificate.setLastUpdateDate(resultSet.getTimestamp(GIFT_CERTIFICATE_LAST_UPDATE_DATE_COLUMN).toLocalDateTime());
        return giftCertificate;
    }
}
