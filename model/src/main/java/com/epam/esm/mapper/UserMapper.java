package com.epam.esm.mapper;

import com.epam.esm.domain.user.User;
import com.epam.esm.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "orders", ignore = true)
    User toEntity(UserDto userDto);
}
