package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.dto.serialization.impl.TagSerializer;
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
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer = new TagSerializer();
    private final TagValidator tagValidator = new TagValidator();
    private final IdValidator idValidator = new IdValidator();

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagRepositoryMock, tagDtoSerializer, tagValidator, idValidator);
    }

    @Test
    void save() {
        Mockito.when(tagRepositoryMock.save(TEST_TAGS.get(0))).thenReturn(1L);
        long actual = tagService.save(TEST_TAGS_DTO.get(0));
        Mockito.verify(tagRepositoryMock).save(TEST_TAGS.get(0));
        Assertions.assertEquals(1L, actual);
    }

    @Test
    void findById() {
        Mockito.when(tagRepositoryMock.findById(1L)).thenReturn(TEST_TAGS.get(0));
        TagDto actual = tagService.findById(1L);
        Mockito.verify(tagRepositoryMock).findById(1L);
        Assertions.assertEquals(TEST_TAGS_DTO.get(0), actual);
    }

    @Test
    void findAll() {
        Mockito.when(tagRepositoryMock.findAll()).thenReturn(TEST_TAGS);
        List<TagDto> tags = tagService.findAll();
        Mockito.verify(tagRepositoryMock).findAll();
        Assertions.assertEquals(TEST_TAGS_DTO, tags);
    }

    @Test
    void countAll() {
        Mockito.when(tagRepositoryMock.countAll()).thenReturn(TAG_LIST_LENGTH);
        int countOfTags = tagService.countAll();
        Mockito.verify(tagRepositoryMock).countAll();
        Assertions.assertEquals(TAG_LIST_LENGTH, countOfTags);
    }

    @Test
    void delete() {
        Mockito.when(tagRepositoryMock.delete(Mockito.anyLong())).thenReturn(1);
        int actual = tagService.delete(1L);
        Mockito.verify(tagRepositoryMock).delete(Mockito.anyLong());
        Assertions.assertEquals(1, actual);
    }

    @Test
    void findNonExistingTagByIdTest() {
        Mockito.when(tagRepositoryMock.findById(10L)).thenThrow(RepositoryException.class);
        Assertions.assertThrows(RepositoryException.class, () -> tagService.findById(10L));
    }

    @Test
    void deleteNonExistingTag() {
        Mockito.when(tagRepositoryMock.delete(10L)).thenThrow(RepositoryException.class);
        Assertions.assertThrows(RepositoryException.class, () -> tagService.delete(10L));
    }
}