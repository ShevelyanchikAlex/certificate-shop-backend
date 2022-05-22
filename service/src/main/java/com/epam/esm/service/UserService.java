package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(Long id);

    List<UserDto> findAll(Integer page, Integer size);

    List<OrderDto> findUserOrders(Long id);
}
