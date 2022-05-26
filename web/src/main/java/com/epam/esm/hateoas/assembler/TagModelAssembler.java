package com.epam.esm.hateoas.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.model.TagModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagDto, TagModel> {

    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    @Override
    public TagModel toModel(TagDto entity) {
        TagModel tagModel = createModelWithId(entity.getId(), entity);
        tagModel.setId(entity.getId());
        tagModel.setName(entity.getName());
        return tagModel;
    }

    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends TagDto> entities) {
        List<TagModel> tagModels = new ArrayList<>();
        entities.forEach(tagDto -> tagModels.add(toModel(tagDto)));
        return CollectionModel.of(tagModels);
    }
}
