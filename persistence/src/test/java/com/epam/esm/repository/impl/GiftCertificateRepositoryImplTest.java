package com.epam.esm.repository.impl;

import com.epam.esm.config.DevPersistenceConfig;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.filter.condition.FilterCondition;
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
        giftCertificateRepository.save(new GiftCertificate(0, "New Gift Certificate", "DescriptionUpd new", 20, 2,
                DATE_TIME, DATE_TIME));
        boolean actual = giftCertificateRepository.existsGiftCertificateByName("New Gift Certificate");
        Assertions.assertTrue(actual);
    }

    @Test
    void findById() {
        String expected = "Nike";
        GiftCertificate actual = giftCertificateRepository.findById(1L);
        Assertions.assertEquals(expected, actual.getName());
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(giftCertificateRepository.findAll());
    }

    @Test
    void findWithFilter() {
        FilterCondition filterCondition = new FilterCondition();
        filterCondition.setDescription("Swim");
        filterCondition.setSortDirection("DESC");
        int expected = 1;
        List<GiftCertificate> actual = giftCertificateRepository.findWithFilter(filterCondition)
                .stream().distinct().collect(Collectors.toList());
        Assertions.assertEquals(expected, actual.size());
    }

    @Test
    void update() {
        int expected = 1;
        int actual = giftCertificateRepository.update(new GiftCertificate(1, "Nike", "DescriptionUpd upd", 22, 2,
                DATE_TIME, DATE_TIME));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int expected = 1;
        int actual = giftCertificateRepository.delete(2L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void existsGiftCertificateByName() {
        Assertions.assertTrue(giftCertificateRepository.existsGiftCertificateByName("Nike"));
    }
}