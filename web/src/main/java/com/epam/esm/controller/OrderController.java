package com.epam.esm.controller;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.processor.OrderModelProcessor;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;
    private final OrderModelProcessor orderModelProcessor;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public OrderDto save(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.save(createOrderDto.getUserId(), createOrderDto.getGiftCertificatesId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public CollectionModel<OrderModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderPage = orderService.findAll(PageRequest.of(pageIndex, size));
        CollectionModel<OrderModel> collectionModel = orderModelAssembler.toCollectionModel(orderPage.getContent());
        return orderModelProcessor.process(orderPage, collectionModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public OrderDto findById(@PathVariable long id) {
        return orderService.findById(id);
    }
}
