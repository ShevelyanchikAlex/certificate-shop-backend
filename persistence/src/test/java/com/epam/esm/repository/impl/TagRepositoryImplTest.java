package com.epam.esm.repository.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.config.DevPersistenceConfig;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(classes = DevPersistenceConfig.class)
@ActiveProfiles("dev")
class TagRepositoryImplTest {
    @Autowired
    private TagRepository tagRepository;

    @IgnoreForBinding
    void save() {
        //given
        Tag tag = new Tag();
        tag.setName("#test_tag");
        int preCount = tagRepository.countAll();
        tagRepository.save(tag);
        System.out.println(preCount);
        //when
        int postCount = tagRepository.countAll();
        System.out.println(postCount);
        //then
        Assertions.assertTrue(preCount < postCount);
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

    @IgnoreForBinding
    void delete() {
        //given
        Tag tag = new Tag();
        tag.setName("#new_tag");
        tagRepository.save(tag);
        //when
        tagRepository.delete(2L);
        boolean actual = tagRepository.existsTagByName("#cool");
        //then
        Assertions.assertFalse(actual);
    }
}