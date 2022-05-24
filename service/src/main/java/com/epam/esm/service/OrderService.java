package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.pagination.Page;

import java.util.List;

public interface OrderService {
    /**
     *
     * Saves Order
     * @param userId Id of User
     * @param giftCertificatesId List with Ids of GiftCertificates
     * @return Saved OrderDto
     */
    OrderDto save(Long userId, List<Long> giftCertificatesId);

    /**
     * Finds OrderDto by Id
     *
     * @param id Id of OrderDto
     * @return Found OrderDto
     */
    OrderDto findById(Long id);

    /**
     * Finds all OrdersDto
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @return Page with OrdersDto
     */
    Page<OrderDto> findAll(Integer pageIndex, Integer size);
}
