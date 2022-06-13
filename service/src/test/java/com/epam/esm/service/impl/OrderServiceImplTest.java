package com.epam.esm.service.impl;

import com.epam.esm.domain.*;
import com.epam.esm.domain.user.Role;
import com.epam.esm.domain.user.Status;
import com.epam.esm.domain.user.User;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.*;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.validator.impl.IdValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderMapperImpl.class, UserMapperImpl.class, GiftCertificateMapperImpl.class, TagMapperImpl.class})
class OrderServiceImplTest {
    private static final List<User> TEST_USERS = List.of(
            new User(1L, "User first", "user1@gmail.com", "user", Role.USER, Status.ACTIVE, Collections.emptyList()),
            new User(2L, "User second", "user2@gmail.com", "user", Role.USER, Status.ACTIVE, Collections.emptyList()));

    private static final List<UserDto> TEST_USERS_DTO = List.of(
            new UserDto(1L, "User first", "user1@gmail.com", "user", Role.USER, Status.ACTIVE),
            new UserDto(2L, "User second", "user2@gmail.com", "user", Role.USER, Status.ACTIVE)
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
    private final OrderMapper orderMapper;
    private final IdValidator idValidator = new IdValidator();

    @Autowired
    OrderServiceImplTest(OrderMapper orderMapper, UserMapper userMapper, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.orderMapper = orderMapper;
    }

    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepositoryMock, giftCertificateRepository,
                userRepositoryMock, orderMapper, idValidator);
    }

    @Test
    void save() {
        //given
        Mockito.when(userRepositoryMock.getById(1L)).thenReturn(TEST_USERS.get(0));
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(TEST_GIFT_CERTIFICATES.get(0));
        Mockito.when(orderRepositoryMock.save(TEST_ORDERS.get(0))).thenReturn(TEST_ORDERS.get(0));
        UserDto expectedUserDto = TEST_ORDERS_DTO.get(0).getUser();
        GiftCertificateDto expectedGiftCertificate = TEST_ORDERS_DTO.get(0).getGiftCertificates().get(0);
        //when
        OrderDto orderDto = orderService.save(1L, List.of(1L));
        UserDto actualUser = orderDto.getUser();
        GiftCertificateDto actualGiftCertificate = orderDto.getGiftCertificates().get(0);
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
        Mockito.when(orderRepositoryMock.findAll(PageRequest.of(1, 10))).thenReturn(TEST_ORDERS);
        //when
        Page<OrderDto> orders = orderService.findAll(PageRequest.of(1, 10));
        //then
        Assertions.assertEquals(TEST_ORDERS_DTO, orders.getContent());
    }
}