package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implemented {@link TagService}
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final DtoConverter<TagDto, Tag> tagDtoConverter;
    private final TagValidator tagValidator;
    private final IdValidator idValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          @Qualifier("tagDtoConverter") DtoConverter<TagDto, Tag> tagDtoConverter,
                          TagValidator tagValidator, IdValidator idValidator) {
        this.tagRepository = tagRepository;
        this.tagDtoConverter = tagDtoConverter;
        this.tagValidator = tagValidator;
        this.idValidator = idValidator;
    }

    @Override
    public TagDto save(TagDto tagDto) {
        if (!tagValidator.validate(tagDto)) {
            throw new ServiceException("tag.validate.error");
        }
        if (tagRepository.existsTagByName(tagDto.getName())) {
            throw new ServiceException("resource.already.exist", "TAG");
        }
        return tagDtoConverter.convertDtoFromEntity(tagRepository.save(tagDtoConverter.convertDtoToEntity(tagDto)));
    }

    @Override
    public TagDto findById(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        Optional<Tag> tagOptional = Optional.ofNullable(tagRepository.findById(id));
        return tagOptional.map(tagDtoConverter::convertDtoFromEntity)
                .orElseThrow(() -> new ServiceException("tag.not.found", id));
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream().map(tagDtoConverter::convertDtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int countAll() {
        return tagRepository.countAll();
    }

    @Override
    public void delete(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        tagRepository.delete(id);
    }
}
