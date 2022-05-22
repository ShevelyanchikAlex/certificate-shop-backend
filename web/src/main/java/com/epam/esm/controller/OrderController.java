package com.epam.esm.controller;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto save(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.save(createOrderDto.getUserId(), createOrderDto.getGiftCertificatesId());
    }

    @GetMapping
    public List<OrderDto> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return orderService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable long id) {
        return orderService.findById(id);
    }
}
