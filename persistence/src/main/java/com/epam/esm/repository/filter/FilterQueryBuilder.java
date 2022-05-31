package com.epam.esm.repository.filter;

import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query Builder to build filter Query depending on {@link GiftCertificateFilterCondition}
 */
@Component
public class FilterQueryBuilder {
    private static final String INIT_QUERY = "SELECT gift_certificate.id FROM gift_certificate " +
            "JOIN gift_certificate_has_tag ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id " +
            "JOIN tag ON gift_certificate_has_tag.tag_id = tag.id ";
    private static final String GIFT_CERTIFICATE_CREATE_DATE = " gift_certificate.create_date ";
    private static final String GIFT_CERTIFICATE_NAME = " gift_certificate.name ";
    private static final String GIFT_CERTIFICATE_TAG_NAME = " tag.name=:tagName";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = " gift_certificate.description ";
    private static final String LIKE_GIFT_CERTIFICATE_NAME_QUERY = " LIKE CONCAT('%',:giftCertificateName,'%')";
    private static final String LIKE_GIFT_CERTIFICATE_DESCRIPTION_QUERY = " LIKE CONCAT('%',:giftCertificateDescription,'%')";
    private static final String ADDITIONAL_PARAM_QUERY = " GROUP BY gift_certificate.id HAVING COUNT(gift_certificate.id)=:count ";
    private static final String SORT_QUERY = " ORDER BY ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String COMMA_SEPARATOR = " , ";
    private static final String NO_EXIST_PARAMETER = "";
    private static final String SINGLE_TAG_NAME = "tagName";
    private static final String SINGLE_GIFT_CERTIFICATE_NAME = "giftCertificateName";
    private static final String SINGLE_GIFT_CERTIFICATE_DESCRIPTION = "giftCertificateDescription";
    private static final String SINGLE_COUNT = "count";

    private Map<String, String> queryParameters;

    /**
     * Builds filter query based on parameters of {@link GiftCertificateFilterCondition}
     *
     * @param giftCertificateFilterCondition {@link GiftCertificateFilterCondition} - contains filter parameters
     * @return {@link QueryBuilderResult} - contains Query and Set with parameters
     */
    public QueryBuilderResult buildQuery(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        StringBuilder filterQuery = new StringBuilder(INIT_QUERY);
        queryParameters = new HashMap<>();
        filterQuery.append(buildNameQuery(giftCertificateFilterCondition.getName()));
        filterQuery.append(buildDescriptionQuery(giftCertificateFilterCondition.getDescription()));
        filterQuery.append(buildTagNamesQuery(giftCertificateFilterCondition.getTagNames()));
        filterQuery.append(buildSortByParameterQuery(giftCertificateFilterCondition));
        return new QueryBuilderResult(filterQuery.toString(), queryParameters);
    }

    private String buildNameQuery(String name) {
        if (name != null) {
            boolean isWhereParametersEmpty = queryParameters.isEmpty();
            queryParameters.put(SINGLE_GIFT_CERTIFICATE_NAME, name);
            return (isWhereParametersEmpty ? WHERE : AND) + GIFT_CERTIFICATE_NAME + LIKE_GIFT_CERTIFICATE_NAME_QUERY;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildDescriptionQuery(String description) {
        if (description != null) {
            boolean isWhereParametersEmpty = queryParameters.isEmpty();
            queryParameters.put(SINGLE_GIFT_CERTIFICATE_DESCRIPTION, description);
            return (isWhereParametersEmpty ? WHERE : AND) + GIFT_CERTIFICATE_DESCRIPTION + LIKE_GIFT_CERTIFICATE_DESCRIPTION_QUERY;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildTagNamesQuery(List<String> tagNames) {
        if (tagNames == null) {
            return NO_EXIST_PARAMETER;
        }
        StringBuilder tagsQuery = new StringBuilder();
        for (int i = 0; i < tagNames.size(); i++) {
            tagsQuery.append(buildTagNameQuery(tagNames.get(i), i, tagNames.size()));
        }
        return tagsQuery.toString();
    }

    private String buildTagNameQuery(String tagName, int index, int size) {
        if (tagName != null) {
            queryParameters.put(SINGLE_TAG_NAME + index, tagName);
            if (index == 0) {
                boolean isWhereParametersEmpty = queryParameters.size() == 1;
                return (isWhereParametersEmpty ? WHERE : AND) + GIFT_CERTIFICATE_TAG_NAME + index;
            }
            if (size - index - 1 == 0) {
                queryParameters.put(SINGLE_COUNT, String.valueOf(size));
                return OR + GIFT_CERTIFICATE_TAG_NAME + index + ADDITIONAL_PARAM_QUERY;
            }
            return OR + GIFT_CERTIFICATE_TAG_NAME + index;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildSortByParameterQuery(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        StringBuilder sortPartQuery = new StringBuilder();
        boolean isOrderConditionContains = false;

        if (giftCertificateFilterCondition.isSortByDate()) {
            sortPartQuery.append(buildSortQuery(GIFT_CERTIFICATE_CREATE_DATE, giftCertificateFilterCondition.getSortDirection(),
                    isOrderConditionContains));
            isOrderConditionContains = true;
        }
        if (giftCertificateFilterCondition.isSortByName()) {
            sortPartQuery.append(buildSortQuery(GIFT_CERTIFICATE_NAME, giftCertificateFilterCondition.getSortDirection(),
                    isOrderConditionContains));
        }
        return sortPartQuery.toString();
    }

    private String buildSortQuery(String param, SortDirection sortDirection, boolean isOrderConditionContains) {
        return (isOrderConditionContains ? COMMA_SEPARATOR : SORT_QUERY) + param +
                (sortDirection == null ? SortDirection.ASC : sortDirection.getSortDirection());
    }
}
