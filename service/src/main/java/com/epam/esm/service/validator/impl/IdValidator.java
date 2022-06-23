package com.epam.esm.service.validator.impl;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.LongPredicate;

/**
 * Implemented {@link Validator} for {@link Long}
 */
@Component
public class IdValidator implements Validator<Long> {
    private static final Long MIN_ID = 0L;
    private static final Long MAX_ID = Long.MAX_VALUE;
    private static final String REQUEST_VALIDATE_ERROR = "request.validate.error";

    @Override
    public void validate(Long id) {
        if (!validateId(id)) {
            throw new ServiceException(REQUEST_VALIDATE_ERROR, id);
        }
    }

    public void validate(List<Long> certificatesId) {
        if (certificatesId == null) {
            throw new ServiceException(REQUEST_VALIDATE_ERROR);
        }
        for (Long id : certificatesId) {
            validate(id);
        }
    }

    private boolean validateId(Long id) {
        if (id == null) {
            return false;
        }
        LongPredicate idPredicate = checkedId -> checkedId > MIN_ID && checkedId < MAX_ID;
        return idPredicate.test(id);
    }
}
