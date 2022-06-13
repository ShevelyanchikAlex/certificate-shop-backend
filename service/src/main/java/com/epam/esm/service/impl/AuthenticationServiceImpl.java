package com.epam.esm.service.impl;

import com.epam.esm.domain.user.User;
import com.epam.esm.dto.AuthenticationRequestDTO;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String EMAIL = "email";
    private static final String TOKEN = "token";

    @Override
    public Map<Object, Object> login(AuthenticationRequestDTO authenticationRequestDTO) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()));
        User user = userRepository.findByEmail(authenticationRequestDTO.getEmail()).orElseThrow(() ->
                new ServiceException("user.not.exist"));
        String token = jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put(EMAIL, authenticationRequestDTO.getEmail());
        response.put(TOKEN, token);
        return response;
    }
}
