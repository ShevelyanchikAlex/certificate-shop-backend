package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.assembler.GiftCertificateModelAssembler;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import com.epam.esm.hateoas.processor.GiftCertificateModelProcessor;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateModelAssembler giftCertificateAssembler;
    private final GiftCertificateModelProcessor giftCertificateModelProcessor;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public GiftCertificateModel save(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateAssembler.toModel(giftCertificateService.save(giftCertificateDto));
    }

    @GetMapping("/{id}")
    public GiftCertificateModel findById(@PathVariable long id) {
        return giftCertificateAssembler.toModel(giftCertificateService.findById(id));
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<GiftCertificateModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<GiftCertificateDto> giftCertificatePage = giftCertificateService.findAll(PageRequest.of(pageIndex, size));
        CollectionModel<GiftCertificateModel> collectionModel = giftCertificateAssembler.toCollectionModel(giftCertificatePage.getContent());
        return giftCertificateModelProcessor.process(giftCertificatePage, collectionModel);
    }

    @GetMapping("/filter")
    public CollectionModel<GiftCertificateModel> findWithFilter(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                @RequestBody GiftCertificateFilterCondition giftCertificateFilterCondition) {
        Page<GiftCertificateDto> giftCertificatePage = giftCertificateService.findWithFilter(PageRequest.of(pageIndex, size), giftCertificateFilterCondition);
        CollectionModel<GiftCertificateModel> collectionModel = giftCertificateAssembler.toCollectionModel(giftCertificatePage.getContent());
        return giftCertificateModelProcessor.process(giftCertificatePage, collectionModel);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public GiftCertificateModel update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateAssembler.toModel(giftCertificateService.update(giftCertificateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public void delete(@PathVariable long id) {
        giftCertificateService.delete(id);
    }
}
