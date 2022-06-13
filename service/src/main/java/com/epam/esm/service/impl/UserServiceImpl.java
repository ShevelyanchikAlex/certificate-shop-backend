package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final IdValidator idValidator;

    @Override
    public UserDto findById(Long id) {
        idValidator.validate(id);
        User user = Optional.of(userRepository.getById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        return userMapper.toDto(user);
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
        User user = Optional.of(userRepository.getById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        List<OrderDto> orders = user.getOrders()
                .stream().map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(orders, pageable, orders.size());
    }
}
