package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Disabled
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
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
        GiftCertificate actual = giftCertificateRepository.getById(1L);
        //then
        Assertions.assertEquals(expected, actual.getName());
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(giftCertificateRepository.findAll(PageRequest.of(1, 10)));
    }

    @Test
    void update() {
        //given
        GiftCertificate expected = new GiftCertificate(1, "Nike", "DescriptionUpd upd", new BigDecimal(22),
                2, DATE_TIME, DATE_TIME, new ArrayList<>());
        //when
        GiftCertificate actual = giftCertificateRepository.save(expected);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //when
        giftCertificateRepository.deleteById(2L);
        GiftCertificate actual = giftCertificateRepository.getById(2L);
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