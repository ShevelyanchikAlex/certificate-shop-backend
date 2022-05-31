package com.epam.esm.mapper;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto toDto(Tag tag);

    Tag toEntity(TagDto tagDto);
}
