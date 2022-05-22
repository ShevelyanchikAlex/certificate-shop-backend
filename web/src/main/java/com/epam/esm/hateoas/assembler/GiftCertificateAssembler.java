package com.epam.esm.hateoas.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateAssembler extends RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateModel> {
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public GiftCertificateAssembler(TagModelAssembler tagModelAssembler) {
        super(GiftCertificateController.class, GiftCertificateModel.class);
        this.tagModelAssembler = tagModelAssembler;
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateDto entity) {
        GiftCertificateModel giftCertificateModel = createModelWithId(entity.getId(), entity);
        giftCertificateModel.setId(entity.getId());
        giftCertificateModel.setName(entity.getName());
        giftCertificateModel.setDescription(entity.getDescription());
        giftCertificateModel.setDuration(entity.getDuration());
        giftCertificateModel.setPrice(entity.getPrice());
        giftCertificateModel.setCreateDate(entity.getCreateDate());
        giftCertificateModel.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateModel.setTags(entity.getTags()
                .stream().map(tagModelAssembler::toModel).collect(Collectors.toList()));
        return giftCertificateModel;
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificateDto> entities) {
        List<GiftCertificateModel> giftCertificateModels = new ArrayList<>();
        entities.forEach(certificateDto -> {
            giftCertificateModels.add(toModel(certificateDto));
        });
        return CollectionModel.of(giftCertificateModels);
    }
}
