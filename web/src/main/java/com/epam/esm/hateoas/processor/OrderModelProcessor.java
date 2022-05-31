package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelProcessor implements RepresentationModelProcessor<OrderModel> {
    private static final int FIRST_PAGE = 1;

    @Override
    public OrderModel process(OrderModel model) {
        return model;
    }

    public CollectionModel<OrderModel> process(Page<OrderDto> page, CollectionModel<OrderModel> collectionModel) {
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

    private CollectionModel<OrderModel> findAllMethod(int pageIndex, int size) {
        return methodOn(OrderController.class).findAll(pageIndex, size);
    }
}
