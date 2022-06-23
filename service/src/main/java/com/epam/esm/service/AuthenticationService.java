package com.epam.esm.service;

import com.epam.esm.dto.AuthenticationRequestDTO;
import com.epam.esm.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    /**
     * Creates/registers User
     *
     * @param userDto UserDto
     * @return Registered UserDto
     */
    UserDto signup(UserDto userDto);

    /**
     * Authenticated User
     *
     * @param authenticationRequestDTO AuthenticationRequestDTO
     * @return Map with email and token
     * @throws AuthenticationException Thrown when an authentication error occurs
     */
    Map<Object, Object> login(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException;
}
