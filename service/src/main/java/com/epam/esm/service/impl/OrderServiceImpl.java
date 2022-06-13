package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final IdValidator idValidator;

    @Override
    @Transactional
    public OrderDto save(Long userId, List<Long> giftCertificatesId) {
        idValidator.validate(userId);
        idValidator.validate(giftCertificatesId);
        User user = Optional.of(userRepository.getById(userId))
                .orElseThrow(() -> new ServiceException("user.not.found", userId));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificatesId.forEach(id -> giftCertificates.add(Optional.ofNullable(certificateRepository.findById(id))
                .orElseThrow(() -> new ServiceException("gift.certificate.not.found", id))));
        BigDecimal totalPrice = BigDecimal.valueOf(giftCertificates
                .stream().mapToDouble(giftCertificate -> Double.parseDouble(giftCertificate.getPrice().toString())).sum());

        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());
        order.setGiftCertificates(giftCertificates);
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto findById(Long id) {
        idValidator.validate(id);
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new ServiceException("order.not.found", id));
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        List<OrderDto> orderDtoList = orderRepository.findAll(pageable)
                .stream().map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(orderDtoList, pageable, orderRepository.countAll());
    }
}
