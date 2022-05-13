package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class TagServiceImplTest {
    private static final List<Tag> TEST_TAGS = Arrays.asList(
            new Tag(1L, "#tag1"),
            new Tag(2L, "#tag2"),
            new Tag(3L, "#tag3"));

    private static final List<TagDto> TEST_TAGS_DTO = Arrays.asList(
            new TagDto(1L, "#tag1"),
            new TagDto(2L, "#tag2"),
            new TagDto(3L, "#tag3"));
    public static final int TAG_LIST_LENGTH = 3;

    private TagService tagService;
    private final TagRepository tagRepositoryMock = Mockito.mock(TagRepository.class);
    private final DtoConverter<TagDto, Tag> tagDtoConverter = new TagConverter();
    private final TagValidator tagValidator = new TagValidator();
    private final IdValidator idValidator = new IdValidator();

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagRepositoryMock, tagDtoConverter, tagValidator, idValidator);
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
        //given
        Mockito.when(tagRepositoryMock.findById(1L)).thenReturn(TEST_TAGS.get(0));
        //when
        TagDto actual = tagService.findById(1L);
        //then
        Mockito.verify(tagRepositoryMock).findById(1L);
        Assertions.assertEquals(TEST_TAGS_DTO.get(0), actual);
    }

    @Test
    void findAll() {
        //given
        Mockito.when(tagRepositoryMock.findAll()).thenReturn(TEST_TAGS);
        //when
        List<TagDto> actual = tagService.findAll();
        //then
        Mockito.verify(tagRepositoryMock).findAll();
        Assertions.assertEquals(TEST_TAGS_DTO, actual);
    }

    @Test
    void countAll() {
        //given
        Mockito.when(tagRepositoryMock.countAll()).thenReturn(TAG_LIST_LENGTH);
        //when
        int actual = tagService.countAll();
        //then
        Mockito.verify(tagRepositoryMock).countAll();
        Assertions.assertEquals(TAG_LIST_LENGTH, actual);
    }

    @Test
    void delete() {
        //when
        tagService.delete(1L);
        //then
        Mockito.verify(tagRepositoryMock).delete(Mockito.anyLong());
    }

    @Test
    void findNonExistingTagByIdTest() {
        //given
        Mockito.when(tagRepositoryMock.findById(10L)).thenThrow(RepositoryException.class);
        //then
        Assertions.assertThrows(RepositoryException.class, () -> tagService.findById(10L));
    }
}