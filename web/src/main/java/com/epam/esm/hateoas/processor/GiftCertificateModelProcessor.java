package com.epam.esm.hateoas.processor;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelProcessor implements RepresentationModelProcessor<GiftCertificateModel> {
    private static final int FIRST_PAGE = 1;

    @Override
    public GiftCertificateModel process(GiftCertificateModel model) {
        return model;
    }

    public CollectionModel<GiftCertificateModel> process(Page<GiftCertificateDto> page, CollectionModel<GiftCertificateModel> collectionModel) {
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

    private CollectionModel<GiftCertificateModel> findAllMethod(int pageIndex, int size) {
        return methodOn(GiftCertificateController.class).findAll(pageIndex, size);
    }

    public CollectionModel<GiftCertificateModel> process(Page<GiftCertificateDto> page,
                                                         GiftCertificateFilterCondition giftCertificateFilterCondition,
                                                         CollectionModel<GiftCertificateModel> collectionModel) {
        int nextPage = page.nextPageable().getPageNumber();
        int previousPage = page.previousPageable().getPageNumber();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(findWithFilterMethod(previousPage, page.getSize(), giftCertificateFilterCondition))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(findWithFilterMethod(nextPage, page.getSize(), giftCertificateFilterCondition))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(findWithFilterMethod(FIRST_PAGE, page.getSize(), giftCertificateFilterCondition))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(findWithFilterMethod(lastPage, page.getSize(), giftCertificateFilterCondition))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<GiftCertificateModel> findWithFilterMethod(int pageIndex, int size, GiftCertificateFilterCondition giftCertificateFilterCondition) {
        return methodOn(GiftCertificateController.class).findWithFilter(pageIndex, size, giftCertificateFilterCondition);
    }
}
