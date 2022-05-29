package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implemented {@link TagService}
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;
    private final IdValidator idValidator;

    @Override
    public TagDto save(TagDto tagDto) {
        if (!tagValidator.validate(tagDto)) {
            throw new ServiceException("tag.validate.error");
        }
        if (tagRepository.existsTagByName(tagDto.getName())) {
            throw new ServiceException("resource.already.exist", "TAG");
        }
        return tagMapper.toDto(tagRepository.save(tagMapper.toEntity(tagDto)));
    }

    @Override
    public TagDto findById(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error", id);
        }
        Optional<Tag> tagOptional = Optional.ofNullable(tagRepository.findById(id));
        return tagOptional.map(tagMapper::toDto)
                .orElseThrow(() -> new ServiceException("tag.not.found", id));
    }

    @Override
    public Page<TagDto> findAll(Pageable pageable) {
        List<TagDto> tags = tagRepository.findAll(pageable)
                .stream().map(tagMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(tags, pageable, tagRepository.countAll());
    }

    @Override
    public Page<TagDto> findMostPopularTags(Pageable pageable) {
        List<TagDto> tags = tagRepository.findMostPopularTags(pageable)
                .stream().map(tagMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(tags, pageable, tagRepository.countAll());
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
