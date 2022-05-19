package com.epam.esm.dto.converter.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Qualifier("orderDtoConverter")
public class OrderConverter implements DtoConverter<OrderDto, Order> {
    private final DtoConverter<GiftCertificateDto, GiftCertificate> giftCertificateDtoConverter;
    private final DtoConverter<UserDto, User> userDtoUserDtoConverter;

    @Autowired
    public OrderConverter(@Qualifier("giftCertificateDtoConverter") DtoConverter<GiftCertificateDto, GiftCertificate> giftCertificateDtoConverter,
                          @Qualifier("userDtoConverter") DtoConverter<UserDto, User> userDtoUserDtoConverter) {
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
        this.userDtoUserDtoConverter = userDtoUserDtoConverter;
    }

    @Override
    public OrderDto convertDtoFromEntity(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCreateDate(order.getCreateDate());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUserDto(userDtoUserDtoConverter.convertDtoFromEntity(order.getUser()));
        orderDto.setGiftCertificatesDto(order.getGiftCertificates()
                .stream().map(giftCertificateDtoConverter::convertDtoFromEntity).collect(Collectors.toList()));
        return orderDto;
    }

    @Override
    public Order convertDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCreateDate(orderDto.getCreateDate());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setUser(userDtoUserDtoConverter.convertDtoToEntity(orderDto.getUserDto()));
        order.setGiftCertificates(orderDto.getGiftCertificatesDto()
                .stream().map(giftCertificateDtoConverter::convertDtoToEntity).collect(Collectors.toList()));
        return null;
    }
}
