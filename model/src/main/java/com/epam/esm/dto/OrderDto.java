package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private long id;
    private BigDecimal totalPrice;
    private LocalDateTime createDate;
    private UserDto user;
    private List<GiftCertificateDto> giftCertificates;
}
