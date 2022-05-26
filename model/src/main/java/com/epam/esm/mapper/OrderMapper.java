package com.epam.esm.mapper;

import com.epam.esm.domain.Order;
import com.epam.esm.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GiftCertificateMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);
}
