package com.epam.esm.dto.serialization.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *Implemented {@link DtoSerializer} for {@link Tag} entity.
 */
@Component
@Qualifier("tagDtoSerializer")
public class TagSerializer implements DtoSerializer<TagDto, Tag> {
    @Override
    public TagDto serializeDtoFromEntity(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    @Override
    public Tag serializeDtoToEntity(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}
