package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.model.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelProcessor implements RepresentationModelProcessor<TagModel> {
    private static final int FIRST_PAGE = 1;

    @Override
    public TagModel process(TagModel model) {
        return model;
    }

    public CollectionModel<TagModel> process(Page<TagDto> page, CollectionModel<TagModel> collectionModel) {
        int nextPage = page.nextPageable().getPageNumber();
        int previousPage = page.previousPageable().getPageNumber();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(findAllMethod(previousPage, page.getSize()))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(findAllMethod(nextPage, page.getSize()))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(findAllMethod(FIRST_PAGE, page.getSize()))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(findAllMethod(lastPage, page.getSize()))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<TagModel> findAllMethod(int pageIndex, int size) {
        return methodOn(TagController.class).findAll(pageIndex, size);
    }
}
