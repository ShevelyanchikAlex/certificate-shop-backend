package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.User;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.dto.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.converter.impl.OrderConverter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.dto.converter.impl.UserConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.validator.impl.IdValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class OrderServiceImplTest {
    private static final List<User> TEST_USERS = List.of(
            new User(1L, "User first", Collections.emptyList()),
            new User(2L, "User second", Collections.emptyList()));

    private static final List<UserDto> TEST_USERS_DTO = List.of(
            new UserDto(1L, "User first"),
            new UserDto(2L, "User second")
    );

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);

    private static final List<GiftCertificate> TEST_GIFT_CERTIFICATES = List.of(
            new GiftCertificate(1L, "Certificate first", "Description first", new BigDecimal("100.4"), 1,
                    DATE_TIME, LocalDateTime.now(), Collections.emptyList()),
            new GiftCertificate(2L, "Certificate second", "Description second", new BigDecimal("20.4"), 2,
                    DATE_TIME, LocalDateTime.now(), Collections.emptyList())
    );

    private static final List<GiftCertificateDto> TEST_GIFT_CERTIFICATES_DTO = List.of(
            new GiftCertificateDto(TEST_GIFT_CERTIFICATES.get(0).getId(), TEST_GIFT_CERTIFICATES.get(0).getName(),
                    TEST_GIFT_CERTIFICATES.get(0).getDescription(), TEST_GIFT_CERTIFICATES.get(0).getPrice(),
                    TEST_GIFT_CERTIFICATES.get(0).getDuration(), TEST_GIFT_CERTIFICATES.get(0).getCreateDate(),
                    TEST_GIFT_CERTIFICATES.get(0).getLastUpdateDate(), Collections.emptyList()),
            new GiftCertificateDto(TEST_GIFT_CERTIFICATES.get(1).getId(), TEST_GIFT_CERTIFICATES.get(1).getName(),
                    TEST_GIFT_CERTIFICATES.get(1).getDescription(), TEST_GIFT_CERTIFICATES.get(1).getPrice(),
                    TEST_GIFT_CERTIFICATES.get(1).getDuration(), TEST_GIFT_CERTIFICATES.get(1).getCreateDate(),
                    TEST_GIFT_CERTIFICATES.get(1).getLastUpdateDate(), Collections.emptyList()));

    private static final List<Order> TEST_ORDERS = List.of(
            new Order(1L, new BigDecimal("100.4"), DATE_TIME, TEST_USERS.get(0), List.of(TEST_GIFT_CERTIFICATES.get(0))),
            new Order(2L, new BigDecimal("0.0"), DATE_TIME, TEST_USERS.get(1), List.of(TEST_GIFT_CERTIFICATES.get(1)))
    );

    private static final List<OrderDto> TEST_ORDERS_DTO = List.of(
            new OrderDto(1L, new BigDecimal("100.4"), DATE_TIME, TEST_USERS_DTO.get(0), List.of(TEST_GIFT_CERTIFICATES_DTO.get(0))),
            new OrderDto(2L, new BigDecimal("0.0"), DATE_TIME, TEST_USERS_DTO.get(1), List.of(TEST_GIFT_CERTIFICATES_DTO.get(1))));

    private OrderService orderService;
    private final OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    private final GiftCertificateRepository giftCertificateRepository = Mockito.mock(GiftCertificateRepository.class);
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final DtoConverter<OrderDto, Order> orderDtoConverterMock = new OrderConverter(
            new GiftCertificateConverter(new TagConverter()), new UserConverter());
    private final IdValidator idValidator = new IdValidator();

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepositoryMock, giftCertificateRepository,
                userRepositoryMock, orderDtoConverterMock, idValidator);
    }

    @Test
    void save() {
        //given
        Mockito.when(userRepositoryMock.findById(1L)).thenReturn(TEST_USERS.get(0));
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(TEST_GIFT_CERTIFICATES.get(0));
        Mockito.when(orderRepositoryMock.save(TEST_ORDERS.get(0))).thenReturn(TEST_ORDERS.get(0));
        UserDto expectedUserDto = TEST_ORDERS_DTO.get(0).getUserDto();
        GiftCertificateDto expectedGiftCertificate = TEST_ORDERS_DTO.get(0).getGiftCertificatesDto().get(0);
        //when
        OrderDto orderDto = orderService.save(1L, List.of(1L));
        UserDto actualUser = orderDto.getUserDto();
        GiftCertificateDto actualGiftCertificate = orderDto.getGiftCertificatesDto().get(0);
        //then
        Assertions.assertEquals(expectedUserDto, actualUser);
        Assertions.assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void findById() {
        //given
        Mockito.when(orderRepositoryMock.findById(1L)).thenReturn(TEST_ORDERS.get(0));
        //when
        OrderDto actual = orderService.findById(1L);
        //then
        Assertions.assertEquals(TEST_ORDERS_DTO.get(0), actual);
    }

    @Test
    void findAll() {
        //given
        Mockito.when(orderRepositoryMock.findAll(1, 10)).thenReturn(TEST_ORDERS);
        //when
        Page<OrderDto> orders = orderService.findAll(1, 10);
        //then
        Assertions.assertEquals(TEST_ORDERS_DTO, orders.getContent());
    }
}