package com.epam.esm.repository.filter;

import com.epam.esm.repository.filter.condition.FilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import org.springframework.stereotype.Component;

@Component
public class FilterQueryBuilder {
    private static final String INIT_QUERY = "SELECT * FROM gift_certificate " +
            "JOIN gift_certificate_has_tag ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id " +
            "JOIN tag ON gift_certificate_has_tag.tag_id = tag.id ";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = "gift_certificate.create_date ";
    private static final String GIFT_CERTIFICATE_NAME = "gift_certificate.name ";
    private static final String GIFT_CERTIFICATE_TAG_NAME = "tag.name ";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "gift_certificate.description ";
    private static final String SORT_QUERY = "ORDER BY ";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String LIKE_START = "LIKE '%";
    private static final String LIKE_END = "%' ";
    private static final String START_VALUE = "='";
    private static final String END_VALUE = "' ";
    private static final String COMMA_SEPARATOR = ", ";


    public String buildQuery(FilterCondition filterCondition) {
        StringBuilder query = new StringBuilder(INIT_QUERY);
        boolean isWhereConditionContains = false;
        boolean isOrderConditionContains = false;

        if (filterCondition.getTagName() != null) {
            query.append(buildTagNameQuery(filterCondition.getTagName()));
            isWhereConditionContains = true;
        }
        if (filterCondition.getName() != null) {
            query.append(buildNameQuery(filterCondition.getName(), isWhereConditionContains));
            isWhereConditionContains = true;
        }
        if (filterCondition.getDescription() != null) {
            query.append(buildDescriptionQuery(filterCondition.getDescription(), isWhereConditionContains));
        }
        if (filterCondition.isSortByDate()) {
            query.append(buildSortQuery(GIFT_CERTIFICATE_CREATE_DATE,
                    filterCondition.getSortDirection() == null ? SortDirection.ASC : SortDirection.sortDirection(filterCondition.getSortDirection()),
                    isOrderConditionContains));
            isOrderConditionContains = true;
        }
        if (filterCondition.isSortByName()) {
            query.append(buildSortQuery(GIFT_CERTIFICATE_NAME,
                    filterCondition.getSortDirection() == null ? SortDirection.ASC : SortDirection.sortDirection(filterCondition.getSortDirection()),
                    isOrderConditionContains));
        }
        return query.toString();
    }

    private String buildTagNameQuery(String tagName) {
        return WHERE + GIFT_CERTIFICATE_TAG_NAME + START_VALUE + tagName + END_VALUE;
    }

    private String buildNameQuery(String name, boolean isWhereConditionContains) {
        return (isWhereConditionContains ? AND : WHERE) + GIFT_CERTIFICATE_NAME + LIKE_START + name + LIKE_END;
    }

    private String buildDescriptionQuery(String description, boolean isWhereConditionContains) {
        return (isWhereConditionContains ? AND : WHERE) + GIFT_CERTIFICATE_DESCRIPTION + LIKE_START + description + LIKE_END;
    }

    private String buildSortQuery(String param, SortDirection sortDirection, boolean isOrderConditionContains) {
        return (isOrderConditionContains ? COMMA_SEPARATOR : SORT_QUERY) + param + sortDirection.name();
    }
}
