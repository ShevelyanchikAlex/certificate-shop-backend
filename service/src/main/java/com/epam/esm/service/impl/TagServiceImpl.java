package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer) {
        this.tagRepository = tagRepository;
        this.tagDtoSerializer = tagDtoSerializer;
    }

    @Override
    public long save(TagDto tagDto) {
        Tag tag = tagDtoSerializer.serializeDtoToEntity(tagDto);
        return tagRepository.save(tag);
    }

    @Override
    public TagDto findById(long id) {
        Tag tag = Optional.ofNullable(tagRepository.findById(id)).orElseThrow(() -> new ServiceException("404", id));
        return tagDtoSerializer.serializeDtoFromEntity(tag);
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(tagDtoSerializer::serializeDtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findByName(String name) {
        Tag tag = Optional.ofNullable(tagRepository.findByName(name)).orElseThrow(() -> new ServiceException("404", name));
        return tagDtoSerializer.serializeDtoFromEntity(tag);
    }

    @Override
    public int delete(long id) {
        int deletedRows = tagRepository.delete(id);
        if (deletedRows == 0) {
            throw new ServiceException("404", id);
        }
        return deletedRows;
    }
}
