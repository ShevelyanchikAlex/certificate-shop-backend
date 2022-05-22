package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.service.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelProcessor implements RepresentationModelProcessor<TagModel> {
    @Override
    public TagModel process(TagModel model) {
        return model;
    }

    public CollectionModel<TagModel> process(Page<TagDto> page, int size, CollectionModel<TagModel> collectionModel) {
        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(findAllTagsMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(findAllTagsMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(findAllTagsMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(findAllTagsMethod(lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<TagModel> findAllTagsMethod(int page, int size) {
        return methodOn(TagController.class).findAll(page, size);
    }
}
