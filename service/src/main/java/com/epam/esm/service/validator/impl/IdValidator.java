package com.epam.esm.service.validator.impl;

import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class IdValidator implements Validator<Long> {
    private static final Long MIN_ID = 0L;
    private static final Long MAX_ID = Long.MAX_VALUE;

    @Override
    public boolean validate(Long id) {
        if (id == null) {
            return false;
        }
        Predicate<Long> idPredicate = checkedId -> checkedId > MIN_ID && checkedId < MAX_ID;
        return idPredicate.test(id);
    }
}
