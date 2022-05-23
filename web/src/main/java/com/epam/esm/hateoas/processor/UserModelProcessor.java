package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.hateoas.model.UserModel;
import com.epam.esm.service.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {
    @Override
    public UserModel process(UserModel model) {
        return model;
    }

    public CollectionModel<UserModel> process(Page<UserDto> page, int size, CollectionModel<UserModel> collectionModel) {
        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(findAllMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(findAllMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(findAllMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(findAllMethod(lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<UserModel> findAllMethod(int page, int size) {
        return methodOn(UserController.class).findAll(page, size);
    }
}
