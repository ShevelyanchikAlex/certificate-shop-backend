package com.epam.esm.repository.filter;

import com.epam.esm.repository.filter.condition.GiftCertificateUpdateCondition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Query Builder to build update Query depending on {@link GiftCertificateUpdateCondition}
 */
@Component
public class UpdateQueryBuilder {
    private static final String START_INIT_QUERY = "UPDATE gift_certificate SET last_update_date=NOW()";
    private static final String END_INIT_QUERY = " WHERE id=?";
    private static final String GIFT_CERTIFICATE_NAME = ", name=? ";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = ", description=? ";
    private static final String GIFT_CERTIFICATE_PRICE = ", price=? ";
    private static final String GIFT_CERTIFICATE_DURATION = ", duration=? ";
    private static final String NO_EXIST_PARAMETER = "";
    public static final int UNRESOLVED_VALUE = 0;

    private Set<String> queryParameters;

    public QueryBuilderResult buildUpdateQuery(GiftCertificateUpdateCondition updateCondition) {
        StringBuilder updateQuery = new StringBuilder(START_INIT_QUERY);
        queryParameters = new LinkedHashSet<>();
        updateQuery.append(buildNameQuery(updateCondition.getName()));
        updateQuery.append(buildDescriptionQuery(updateCondition.getDescription()));
        updateQuery.append(buildPriceQuery(updateCondition.getPrice()));
        updateQuery.append(buildDurationQuery(updateCondition.getDuration()));
        updateQuery.append(buildEndQuery(updateCondition.getId()));
        return new QueryBuilderResult(updateQuery.toString(), queryParameters);
    }


    private String buildNameQuery(String name) {
        if (name != null) {
            queryParameters.add(name);
            return GIFT_CERTIFICATE_NAME;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildDescriptionQuery(String description) {
        if (description != null) {
            queryParameters.add(description);
            return GIFT_CERTIFICATE_DESCRIPTION;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildPriceQuery(Integer price) {
        if (price != null && price != UNRESOLVED_VALUE) {
            queryParameters.add(price.toString());
            return GIFT_CERTIFICATE_PRICE;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildDurationQuery(Integer duration) {
        if (duration != null && duration != UNRESOLVED_VALUE) {
            queryParameters.add(duration.toString());
            return GIFT_CERTIFICATE_DURATION;
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildEndQuery(long id) {
        queryParameters.add(String.valueOf(id));
        return END_INIT_QUERY;
    }
}
