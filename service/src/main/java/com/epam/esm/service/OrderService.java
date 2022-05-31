package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    /**
     * Saves Order
     *
     * @param userId             Id of User
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
     * @param pageable Pageable
     * @return Page with OrdersDto
     */
    Page<OrderDto> findAll(Pageable pageable);
}
