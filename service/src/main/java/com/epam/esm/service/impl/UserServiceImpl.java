package com.epam.esm.service.impl;

import com.epam.esm.domain.user.Role;
import com.epam.esm.domain.user.Status;
import com.epam.esm.domain.user.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final IdValidator idValidator;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) {
        userValidator.validate(userDto);
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new ServiceException("resource.already.exist", "USER");
        }
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        idValidator.validate(id);
        if (!userRepository.existsById(id)) {
            throw new ServiceException("user.not.found", id);
        }
        return userMapper.toDto(userRepository.getById(id));
    }

    @Override
    public UserDto findByEmail(String email) {
        userValidator.validateEmail(email);
        if(!userRepository.existsUserByEmail(email)) {
            throw new ServiceException("user.not.found", email);
        }
        return userMapper.toDto(userRepository.findByEmail(email).get());
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        List<UserDto> users = userRepository.findAll(pageable)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(users, pageable, userRepository.count());
    }

    @Override
    public Page<OrderDto> findUserOrders(Pageable pageable, Long id) {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("user.not.found", id);
        }
        User user = userRepository.getById(id);
        List<OrderDto> orders = user.getOrders()
                .stream().map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(orders, pageable, orders.size());
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }
}
