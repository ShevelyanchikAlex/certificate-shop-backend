package com.epam.esm.service.validator.impl;

import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Implemented {@link Validator} for {@link Long}
 */
@Component
public class IdValidator implements Validator<Long> {
    private static final Long MIN_ID = 0L;
    private static final Long MAX_ID = Long.MAX_VALUE;

    @Override
    public boolean validate(Long id) {
        return validateId(id);
    }

    public boolean validate(List<Long> certificatesId) {
        if (certificatesId == null) {
            return false;
        }
        for (Long id : certificatesId) {
            if (!validate(id)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateId(Long id) {
        if (id == null) {
            return false;
        }
        Predicate<Long> idPredicate = checkedId -> checkedId > MIN_ID && checkedId < MAX_ID;
        return idPredicate.test(id);
    }
}
