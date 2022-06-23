package com.epam.esm.service.impl;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public TagDto save(TagDto tagDto) {
        tagValidator.validate(tagDto);
        if (tagRepository.existsTagByName(tagDto.getName())) {
            throw new ServiceException("resource.already.exist", "TAG");
        }
        return tagMapper.toDto(tagRepository.save(tagMapper.toEntity(tagDto)));
    }

    @Override
    public TagDto findById(Long id) {
        idValidator.validate(id);
        if (!tagRepository.existsById(id)) {
            throw new ServiceException("tag.not.found", id);
        }
        return tagMapper.toDto(tagRepository.getById(id));
    }

    @Override
    public Page<TagDto> findAll(Pageable pageable) {
        List<TagDto> tags = tagRepository.findAll(pageable)
                .stream().map(tagMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(tags, pageable, tagRepository.count());
    }

    @Override
    public Page<TagDto> findMostPopularTags(Pageable pageable) {
        List<TagDto> tags = tagRepository.findMostPopularTags(pageable)
                .stream().map(tagMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(tags, pageable, tagRepository.count());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        idValidator.validate(id);
        if (!tagRepository.existsById(id)) {
            throw new ServiceException("tag.not.found", id);
        }
        tagRepository.deleteById(id);
    }
}
