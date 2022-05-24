package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.service.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelProcessor implements RepresentationModelProcessor<OrderModel> {
    @Override
    public OrderModel process(OrderModel model) {
        return model;
    }

    public CollectionModel<OrderModel> process(Page<OrderDto> page, int size, CollectionModel<OrderModel> collectionModel) {
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

    private CollectionModel<OrderModel> findAllMethod(int pageIndex, int size) {
        return methodOn(OrderController.class).findAll(pageIndex, size);
    }
}
