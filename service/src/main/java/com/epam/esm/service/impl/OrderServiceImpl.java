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
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.pagination.PaginationUtil;
import com.epam.esm.service.validator.impl.IdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public OrderDto save(Long userId, List<Long> giftCertificatesId) {
        if (!idValidator.validate(userId) || !idValidator.validate(giftCertificatesId)) {
            throw new ServiceException("request.validate.error");
        }
        User user = Optional.ofNullable(userRepository.findById(userId))
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
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new ServiceException("order.not.found", id));
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderDto> findAll(Integer pageIndex, Integer size) {
        pageIndex = PaginationUtil.correctPageIndex(pageIndex, size, orderRepository::countAll);
        List<OrderDto> orderDtoList = orderRepository.findAll(pageIndex, size)
                .stream().map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new Page<>(pageIndex, size, orderRepository.countAll(), orderDtoList);
    }
}
