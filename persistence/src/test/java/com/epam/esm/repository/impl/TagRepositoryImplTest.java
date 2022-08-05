package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@Transactional
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("dev")
class TagRepositoryImplTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void save() {
        //given
        Tag tag = new Tag();
        tag.setName("#test_tag");
        //when
        tag = tagRepository.save(tag);
        //then
        Assertions.assertNotNull(tag);
    }

    @Test
    void findById() {
        //given
        String expected = "#tag";
        //when
        Tag tag = tagRepository.getById(1L);
        String actual = tag.getName();
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByName() {
        //given
        long expected = 3L;
        //when
        Tag tag = tagRepository.getTagByName("#like");
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
        Assertions.assertNotNull(tagRepository.findAll(PageRequest.of(1, 10)));
    }

    @Test
    void delete() {
        //when
        tagRepository.deleteById(2L);
        Tag actual = tagRepository.getById(2L);
        //then
        Assertions.assertNull(actual);
    }
}