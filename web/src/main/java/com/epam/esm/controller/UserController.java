package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.hateoas.assembler.UserModelAssembler;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.model.UserModel;
import com.epam.esm.hateoas.processor.UserModelProcessor;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final UserModelProcessor userModelProcessor;
    private final OrderModelAssembler orderModelAssembler;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public UserModel findById(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public CollectionModel<UserModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<UserDto> userPage = userService.findAll(PageRequest.of(pageIndex, size));
        CollectionModel<UserModel> collectionModel = userModelAssembler.toCollectionModel(userPage.getContent());
        return userModelProcessor.process(userPage, collectionModel);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public CollectionModel<OrderModel> findUserOrders(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                      @PathVariable Long id) {
        Page<OrderDto> orderPage = userService.findUserOrders(PageRequest.of(pageIndex, size), id);
        CollectionModel<OrderModel> collectionModel = orderModelAssembler.toCollectionModel(orderPage.getContent());
        return userModelProcessor.process(orderPage, collectionModel, id);
    }
}
