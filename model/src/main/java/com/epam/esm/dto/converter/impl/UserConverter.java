package com.epam.esm.dto.converter.impl;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userDtoConverter")
public class UserConverter implements DtoConverter<UserDto, User> {
    @Override
    public UserDto convertDtoFromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    @Override
    public User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        return user;
    }
}
