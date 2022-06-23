package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    /**
     * Saves User
     *
     * @param userDto UserDto
     * @return Saved UserDto
     */
    UserDto save(UserDto userDto);

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
     * @param pageable Pageable
     * @return Page with UsersDto
     */
    Page<UserDto> findAll(Pageable pageable);

    /**
     * Finds UserOrders
     *
     * @param pageable Pageable
     * @param id       Id of User
     * @return Page with UserOrders
     */
    Page<OrderDto> findUserOrders(Pageable pageable, Long id);
}
