package com.epam.esm.repository.impl;

import com.epam.esm.config.DevPersistenceConfig;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevPersistenceConfig.class)
@ActiveProfiles("dev")
class GiftCertificateRepositoryImplTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void save() {
        //given
        giftCertificateRepository.save(new GiftCertificate(0, "New Gift Certificate", "DescriptionUpd new", 20, 2,
                DATE_TIME, DATE_TIME));
        //when
        boolean actual = giftCertificateRepository.existsGiftCertificateByName("New Gift Certificate");
        //then
        Assertions.assertTrue(actual);
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

    @Test
    void update() {
        //given
        GiftCertificate expected = new GiftCertificate(1, "Nike", "DescriptionUpd upd", 22, 2,
                DATE_TIME, DATE_TIME);
        //when
        GiftCertificate actual = giftCertificateRepository.update(expected);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //when
        giftCertificateRepository.delete(2L);
        boolean actual = giftCertificateRepository.existsGiftCertificateByName("Restaurant");
        //then
        Assertions.assertFalse(actual);
    }

    @Test
    void existsGiftCertificateByName() {
        //when
        boolean actual = giftCertificateRepository.existsGiftCertificateByName("Nike");
        //then
        Assertions.assertTrue(actual);
    }
}