package com.epam.esm.service.validator.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.Validator;
import com.epam.esm.service.validator.ValidatorRegexPattern;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Implemented {@link Validator} for {@link UserDto}
 */
@Component
public class UserValidator implements Validator<UserDto> {
    @Override
    public void validate(UserDto userDto) {
        if ((userDto == null) || !(validateName(userDto.getName()) &&
                validateEmail(userDto.getEmail()) &&
                validatePassword(userDto.getPassword()))) {
            throw new ServiceException("user.validate.error");
        }
    }

    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Predicate<String> userEmailPredicate = str -> str.matches(ValidatorRegexPattern.EMAIL_REGEX_PATTERN);
        return userEmailPredicate.test(email);
    }

    private boolean validateName(String name) {
        if (name == null) {
            return false;
        }
        Predicate<String> userNamePredicate = str -> str.matches(ValidatorRegexPattern.USER_NAME_REGEX_PATTERN);
        return userNamePredicate.test(name);
    }

    private boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        Predicate<String> userPasswordPredicate = str -> str.matches(ValidatorRegexPattern.PASSWORD_REGEX_PATTERN);
        return userPasswordPredicate.test(password);
    }
}
