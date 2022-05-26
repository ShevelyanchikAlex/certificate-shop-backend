package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.*;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.validator.impl.IdValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserMapperImpl.class, OrderMapperImpl.class,
        GiftCertificateMapperImpl.class, TagMapperImpl.class})
class UserServiceImplTest {
    private static final List<User> TEST_USERS = List.of(
            new User(1L, "User first", Collections.emptyList()),
            new User(2L, "User second", Collections.emptyList()));

    private UserService userService;
    private final UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final IdValidator idValidator = new IdValidator();

    @Autowired
    UserServiceImplTest(UserMapper userMapper, OrderMapper orderMapper, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepositoryMock, userMapper, orderMapper, idValidator);
    }

    @Test
    void findById() {
        //given
        Mockito.when(userRepositoryMock.findById(1L)).thenReturn(TEST_USERS.get(0));
        String expected = TEST_USERS.get(0).getName();
        //when
        UserDto userDto = userService.findById(1L);
        String actual = userDto.getName();
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        //given
        Mockito.when(userRepositoryMock.findAll(1, 10)).thenReturn(TEST_USERS);
        int expected = TEST_USERS.size();
        //when
        Page<UserDto> userPage = userService.findAll(1, 10);
        int actual = userPage.getContent().size();
        //then
        Assertions.assertNotNull(userPage);
        Assertions.assertEquals(expected, actual);
    }
}