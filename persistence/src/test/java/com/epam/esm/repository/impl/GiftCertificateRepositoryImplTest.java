package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.config.DevPersistenceConfig;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevPersistenceConfig.class)
@ActiveProfiles("dev")
class GiftCertificateRepositoryImplTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void save() {
        //given
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("New Gift Certificate");
        giftCertificate.setDescription("DescriptionUpd new");
        giftCertificate.setPrice(new BigDecimal(20));
        giftCertificate.setDuration(2);
        giftCertificate.setCreateDate(DATE_TIME);
        giftCertificate.setLastUpdateDate(DATE_TIME);
        //when
        giftCertificate = giftCertificateRepository.save(giftCertificate);
        //then
        Assertions.assertNotNull(giftCertificate);
    }

    @Test
    void findById() {
        //given
        String expected = "Nike";
        //when
        GiftCertificate actual = giftCertificateRepository.findById(1L);
        //then
        Assertions.assertEquals(expected, actual.getName());
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(giftCertificateRepository.findAll());
    }

    @Test
    void findWithFilter() {
        //given
        GiftCertificateFilterCondition giftCertificateFilterCondition = new GiftCertificateFilterCondition();
        giftCertificateFilterCondition.setDescription("Swim");
        giftCertificateFilterCondition.setSortDirection(SortDirection.DESC);
        int expected = 1;
        //when
        List<GiftCertificate> actual = giftCertificateRepository.findWithFilter(giftCertificateFilterCondition)
                .stream().distinct().collect(Collectors.toList());
        //then
        Assertions.assertEquals(expected, actual.size());
    }

    @IgnoreForBinding
    void update() {
        //given
        GiftCertificate expected = new GiftCertificate(1, "Nike", "DescriptionUpd upd", new BigDecimal(22), 2,
                DATE_TIME, DATE_TIME, new ArrayList<>());
        //when
        GiftCertificate actual = giftCertificateRepository.update(expected);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //when
        giftCertificateRepository.delete(2L);
        GiftCertificate actual = giftCertificateRepository.findById(2L);
        //then
        Assertions.assertNull(actual);
    }

    @Test
    void existsGiftCertificateByName() {
        //when
        boolean actual = giftCertificateRepository.existsGiftCertificateByName("Nike");
        //then
        Assertions.assertTrue(actual);
    }
}