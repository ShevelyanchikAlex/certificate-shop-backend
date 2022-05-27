package com.epam.esm.controller;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.processor.OrderModelProcessor;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;
    private final OrderModelProcessor orderModelProcessor;

    @PostMapping
    public OrderDto save(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.save(createOrderDto.getUserId(), createOrderDto.getGiftCertificatesId());
    }

    @GetMapping
    public CollectionModel<OrderModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderPage = orderService.findAll(pageIndex, size);
        CollectionModel<OrderModel> collectionModel = orderModelAssembler.toCollectionModel(orderPage.getContent());
        return orderModelProcessor.process(orderPage, size, collectionModel);
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable long id) {
        return orderService.findById(id);
    }
}
