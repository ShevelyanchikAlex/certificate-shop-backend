package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.assembler.UserModelAssembler;
import com.epam.esm.hateoas.model.UserModel;
import com.epam.esm.hateoas.processor.UserModelProcessor;
import com.epam.esm.service.UserService;
import com.epam.esm.service.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final UserModelProcessor userModelProcessor;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler, UserModelProcessor userModelProcessor) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.userModelProcessor = userModelProcessor;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public CollectionModel<UserModel> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
//        return userService.findAll(page, size);
        Page<UserDto> userPage = userService.findAll(page, size);
        CollectionModel<UserModel> collectionModel = userModelAssembler.toCollectionModel(userPage.getContent());
        return userModelProcessor.process(userPage, size, collectionModel);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> findUserOrders(@PathVariable Long id) {
        return userService.findUserOrders(id);
    }
}
