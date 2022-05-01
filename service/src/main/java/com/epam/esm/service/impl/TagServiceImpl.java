package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implemented {@link TagService}
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;
    private final TagValidator tagValidator;
    private final IdValidator idValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer,
                          TagValidator tagValidator, IdValidator idValidator) {
        this.tagRepository = tagRepository;
        this.tagDtoSerializer = tagDtoSerializer;
        this.tagValidator = tagValidator;
        this.idValidator = idValidator;
    }

    @Override
    public TagDto save(TagDto tagDto) {
        if (!tagValidator.validate(tagDto)) {
            throw new ServiceException(ServiceErrorCode.TAG_VALIDATE_ERROR);
        }
        if (tagRepository.existsTagByName(tagDto.getName())) {
            throw new ServiceException(ServiceErrorCode.RESOURCE_ALREADY_EXIST, "TAG");
        }
        return tagDtoSerializer.serializeDtoFromEntity(tagRepository.save(tagDtoSerializer.serializeDtoToEntity(tagDto)));
    }

    @Override
    public TagDto findById(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR, id);
        }
        Tag tag = tagRepository.findById(id);
        return tagDtoSerializer.serializeDtoFromEntity(tag);
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(tagDtoSerializer::serializeDtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int countAll() {
        return tagRepository.countAll();
    }

    @Override
    public int delete(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR, id);
        }
        return tagRepository.delete(id);
    }
}
