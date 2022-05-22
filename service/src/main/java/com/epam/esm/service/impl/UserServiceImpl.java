package com.epam.esm.service.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoConverter<UserDto, User> userDtoUserDtoConverter;
    private final DtoConverter<OrderDto, Order> orderDtoOrderDtoConverter;
    private final IdValidator idValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Qualifier("userDtoConverter") DtoConverter<UserDto, User> userDtoUserDtoConverter,
                           @Qualifier("orderDtoConverter") DtoConverter<OrderDto, Order> orderDtoOrderDtoConverter, IdValidator idValidator) {
        this.userRepository = userRepository;
        this.userDtoUserDtoConverter = userDtoUserDtoConverter;
        this.orderDtoOrderDtoConverter = orderDtoOrderDtoConverter;
        this.idValidator = idValidator;
    }

    @Override
    public UserDto findById(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        return userDtoUserDtoConverter.convertDtoFromEntity(user);
    }

    @Override
    public List<UserDto> findAll(Integer page, Integer size) {
        return userRepository.findAll(page, size)
                .stream().map(userDtoUserDtoConverter::convertDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findUserOrders(Long id) {
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new ServiceException("user.not.found", id));
        return user.getOrders()
                .stream().map(orderDtoOrderDtoConverter::convertDtoFromEntity).collect(Collectors.toList());
    }
}
