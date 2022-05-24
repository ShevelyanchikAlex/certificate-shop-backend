package com.epam.esm.hateoas.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {
    private final UserModelAssembler userModelAssembler;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @Autowired
    public OrderModelAssembler(UserModelAssembler userModelAssembler, GiftCertificateModelAssembler giftCertificateModelAssembler) {
        super(OrderController.class, OrderModel.class);
        this.userModelAssembler = userModelAssembler;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    @Override
    public OrderModel toModel(OrderDto entity) {
        OrderModel orderModel = createModelWithId(entity.getId(), entity);
        orderModel.setId(entity.getId());
        orderModel.setCreateDate(entity.getCreateDate());
        orderModel.setTotalPrice(entity.getTotalPrice());
        orderModel.setUser(userModelAssembler.toModel(entity.getUserDto()));
        orderModel.setGiftCertificates(entity.getGiftCertificatesDto()
                .stream().map(giftCertificateModelAssembler::toModel).collect(Collectors.toList()));
        return orderModel;
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends OrderDto> entities) {
        List<OrderModel> orderModels = new ArrayList<>();
        entities.forEach(orderDto -> {
            orderModels.add(toModel(orderDto));
        });
        return CollectionModel.of(orderModels);
    }
}
