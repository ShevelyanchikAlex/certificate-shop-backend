package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
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
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> findAll(Integer pageIndex, Integer size) {
        List<UserDto> users = userRepository.findAll(pageIndex, size)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new Page<>(pageIndex, size, userRepository.countAll(), users);
    }

    @Override
    public Page<OrderDto> findUserOrders(Integer pageIndex, Integer size, Long id) {
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        List<OrderDto> orders = user.getOrders()
                .stream().skip((long) (pageIndex - 1) * size).limit(size)
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new Page<>(pageIndex, size, orders.size(), orders);
    }
}
