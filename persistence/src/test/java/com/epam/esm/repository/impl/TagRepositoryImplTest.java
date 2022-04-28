package com.epam.esm.repository.impl;

import com.epam.esm.config.DevPersistenceConfig;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevPersistenceConfig.class)
@ActiveProfiles("dev")
class TagRepositoryImplTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void save() {
        long expected = 1L;
        long actual = tagRepository.save(new Tag(1L, "#test"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {
        String expected = "#tag";
        Tag tag = tagRepository.findById(1L);
        String actual = tag.getName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByName() {
        long expected = 3L;
        Tag tag = tagRepository.findByName("#like");
        long actual = tag.getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void existsTagByName() {
        Assertions.assertTrue(tagRepository.existsTagByName("#like"));
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(tagRepository.findAll());
    }

    @Test
    void findAllByGiftCertificateId() {
        Set<Tag> expected = Set.of(new Tag(1L, "#tag"),
                new Tag(3L, "#like"));
        Set<Tag> actual = tagRepository.findAllByGiftCertificateId(1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int expected = 1;
        tagRepository.save(new Tag(4L, "#new_tag"));
        int actual = tagRepository.delete(2L);
        Assertions.assertEquals(expected, actual);
    }
}