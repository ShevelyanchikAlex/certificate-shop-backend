package com.epam.esm.repository.mapper;

import com.epam.esm.domain.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    private static final String TAG_ID_COLUMN = "id";
    private static final String TAG_NAME_COLUMN = "name";

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(TAG_ID_COLUMN));
        tag.setName(resultSet.getString(TAG_NAME_COLUMN));
        return tag;
    }
}
