package com.epam.esm.dto.converter.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *Implemented {@link DtoConverter} for {@link Tag} entity.
 */
@Component
@Qualifier("tagDtoConverter")
public class TagConverter implements DtoConverter<TagDto, Tag> {
    @Override
    public TagDto convertDtoFromEntity(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    @Override
    public Tag convertDtoToEntity(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}
