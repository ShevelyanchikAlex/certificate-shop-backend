package com.epam.esm.service;

import com.epam.esm.dto.AuthenticationRequestDTO;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> login(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException;
}
