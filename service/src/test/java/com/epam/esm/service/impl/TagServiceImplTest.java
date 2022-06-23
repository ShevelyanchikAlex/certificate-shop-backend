package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.TagMapperImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TagMapperImpl.class})
class TagServiceImplTest {
    private static final List<Tag> TEST_TAGS = Arrays.asList(
            new Tag(1L, "#tag1"),
            new Tag(2L, "#tag2"),
            new Tag(3L, "#tag3"));

    private static final List<TagDto> TEST_TAGS_DTO = Arrays.asList(
            new TagDto(1L, "#tag1"),
            new TagDto(2L, "#tag2"),
            new TagDto(3L, "#tag3"));
    public static final long TAG_LIST_LENGTH = 3;

    private TagService tagService;
    private final TagRepository tagRepositoryMock = Mockito.mock(TagRepository.class);
    private final TagMapper tagMapper;
    private final TagValidator tagValidator = new TagValidator();
    private final IdValidator idValidator = new IdValidator();

    @Autowired
    TagServiceImplTest(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagRepositoryMock, tagMapper, tagValidator, idValidator);
    }

    @Test
    void save() {
        //given
        Mockito.when(tagRepositoryMock.save(TEST_TAGS.get(0))).thenReturn(TEST_TAGS.get(0));
        String expected = TEST_TAGS.get(0).getName();
        //when
        TagDto savedTagDto = tagService.save(TEST_TAGS_DTO.get(0));
        String actual = savedTagDto.getName();
        //then
        Mockito.verify(tagRepositoryMock).save(TEST_TAGS.get(0));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {
        //then
        Assertions.assertThrows(ServiceException.class, () -> tagService.findById(1L));
    }

    @Test
    void findAll() {
        //given
        Mockito.when(tagRepositoryMock.findAll(PageRequest.of(1, 3))).thenReturn(new PageImpl<>(TEST_TAGS));
        //when
        Page<TagDto> actual = tagService.findAll(PageRequest.of(1, 3));
        //then
        Mockito.verify(tagRepositoryMock).findAll(PageRequest.of(1, 3));
    }

    @Test
    void delete() {
        //then
        Assertions.assertThrows(ServiceException.class, () -> tagService.delete(1L));
    }

    @Test
    void findNonExistingTagByIdTest() {
        //then
        Assertions.assertThrows(ServiceException.class, () -> tagService.findById(10L));
    }
}