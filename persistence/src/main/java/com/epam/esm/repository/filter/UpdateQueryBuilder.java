package com.epam.esm.repository.filter;

import com.epam.esm.repository.filter.condition.UpdateCondition;
import org.springframework.stereotype.Component;

@Component
public class UpdateQueryBuilder {
    private static final String START_INIT_QUERY = "UPDATE gift_certificate SET last_update_date=NOW()";
    private static final String END_INIT_QUERY = " WHERE id=%d";
    private static final String GIFT_CERTIFICATE_NAME = ", name='%s' ";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = ", description='%s' ";
    private static final String GIFT_CERTIFICATE_PRICE = ", price=%d ";
    private static final String GIFT_CERTIFICATE_DURATION = ", duration=%d ";
    private static final String NO_EXIST_PARAMETER = "";

    public String buildUpdateQuery(UpdateCondition updateCondition) {
        StringBuilder updateQuery = new StringBuilder(START_INIT_QUERY);
        updateQuery.append(buildNameQuery(updateCondition.getName()));
        updateQuery.append(buildDescriptionQuery(updateCondition.getDescription()));
        updateQuery.append(buildPriceQuery(updateCondition.getPrice()));
        updateQuery.append(buildDurationQuery(updateCondition.getDuration()));
        updateQuery.append(buildEndQuery(updateCondition.getId()));
        return new String(updateQuery);
    }


    private String buildNameQuery(String name) {
        if (name != null) {
            return String.format(GIFT_CERTIFICATE_NAME, name);
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildDescriptionQuery(String description) {
        if (description != null) {
            return String.format(GIFT_CERTIFICATE_DESCRIPTION, description);
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildPriceQuery(Integer price) {
        if (price != null) {
            return String.format(GIFT_CERTIFICATE_PRICE, price);
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildDurationQuery(Integer duration) {
        if (duration != null) {
            return String.format(GIFT_CERTIFICATE_DURATION, duration);
        }
        return NO_EXIST_PARAMETER;
    }

    private String buildEndQuery(long id) {
        return String.format(END_INIT_QUERY, id);
    }
}
