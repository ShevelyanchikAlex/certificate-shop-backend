package com.epam.esm.service.validator.impl;

import com.epam.esm.service.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class IdValidator implements Validator<Long> {
    private static final Long idMin = 0L;
    private static final Long idMax = Long.MAX_VALUE;

    @Override
    public boolean validate(Long id) {
        if (id == null) {
            return false;
        }
        Predicate<Long> idPredicate = checkedId -> checkedId > idMin && checkedId < idMax;
        return idPredicate.test(id);
    }
}
