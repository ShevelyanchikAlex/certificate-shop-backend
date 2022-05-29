package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {
    private static final int FIRST_PAGE = 1;

    @Override
    public UserModel process(UserModel model) {
        return model;
    }

    public CollectionModel<UserModel> process(Page<UserDto> page, CollectionModel<UserModel> collectionModel) {
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

    private CollectionModel<UserModel> findAllMethod(int pageIndex, int size) {
        return methodOn(UserController.class).findAll(pageIndex, size);
    }

    public CollectionModel<OrderModel> process(Page<OrderDto> page, CollectionModel<OrderModel> collectionModel, Long id) {
        int nextPage = page.hasNext() ? page.nextPageable().getPageNumber() : FIRST_PAGE;
        int previousPage = page.hasPrevious() ? page.previousPageable().getPageNumber() : page.getTotalPages();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(findUserOrders(previousPage, page.getSize(), id))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(findUserOrders(nextPage, page.getSize(), id))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(findUserOrders(FIRST_PAGE, page.getSize(), id))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(findUserOrders(lastPage, page.getSize(), id))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<OrderModel> findUserOrders(int pageIndex, int size, Long id) {
        return methodOn(UserController.class).findUserOrders(pageIndex, size, id);
    }
}
