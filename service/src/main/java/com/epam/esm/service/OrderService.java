package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto save(Long userId, List<Long> giftCertificatesId);

    OrderDto findById(Long id);

    List<OrderDto> findAll();
}
