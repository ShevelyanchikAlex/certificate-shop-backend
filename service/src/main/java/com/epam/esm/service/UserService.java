package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.pagination.Page;

public interface UserService {
    /**
     * Finds UserDto by id
     *
     * @param id Id of User
     * @return Found UserDto
     */
    UserDto findById(Long id);

    /**
     * Finds all UsersDto
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @return Page with UsersDto
     */
    Page<UserDto> findAll(Integer pageIndex, Integer size);

    /**
     * Finds UserOrders
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @param id Id of User
     * @return Page with UserOrders
     */
    Page<OrderDto> findUserOrders(Integer pageIndex, Integer size, Long id);
}
