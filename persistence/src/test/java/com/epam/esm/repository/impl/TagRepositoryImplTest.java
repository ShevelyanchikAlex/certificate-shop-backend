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
        //given
        tagRepository.save(new Tag(1L, "#test"));
        //when
        boolean actual = tagRepository.existsTagByName("#test");
        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void findById() {
        //given
        String expected = "#tag";
        //when
        Tag tag = tagRepository.findById(1L);
        String actual = tag.getName();
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByName() {
        //given
        long expected = 3L;
        //when
        Tag tag = tagRepository.findByName("#like");
        long actual = tag.getId();
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void existsTagByName() {
        //when
        boolean actual = tagRepository.existsTagByName("#like");
        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(tagRepository.findAll());
    }

    @Test
    void findAllByGiftCertificateId() {
        //given
        Set<Tag> expected = Set.of(new Tag(1L, "#tag"),
                new Tag(3L, "#like"));
        //when
        Set<Tag> actual = tagRepository.findAllByGiftCertificateId(1L);
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        //given
        int expected = 1;
        tagRepository.save(new Tag(4L, "#new_tag"));
        //when
        int actual = tagRepository.delete(2L);
        //then
        Assertions.assertEquals(expected, actual);
    }
}