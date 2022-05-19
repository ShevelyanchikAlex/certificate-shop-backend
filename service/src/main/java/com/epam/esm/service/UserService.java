package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(Long id);

    List<UserDto> findAll();

    List<OrderDto> findUserOrders(Long id);
}
