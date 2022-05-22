package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import com.epam.esm.hateoas.processor.GiftCertificateModelProcessor;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final GiftCertificateModelProcessor giftCertificateModelProcessor;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler giftCertificateAssembler, GiftCertificateModelProcessor giftCertificateModelProcessor) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateAssembler = giftCertificateAssembler;
        this.giftCertificateModelProcessor = giftCertificateModelProcessor;
    }

    @PostMapping
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.save(giftCertificateDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable long id) {
        return giftCertificateService.findById(id);
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<GiftCertificateModel> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<GiftCertificateDto> giftCertificatePage = giftCertificateService.findAll(page, size);
        CollectionModel<GiftCertificateModel> collectionModel = giftCertificateAssembler.toCollectionModel(giftCertificatePage.getContent());
        return giftCertificateModelProcessor.process(giftCertificatePage, size, collectionModel);
    }

    @GetMapping("/filter")
    public CollectionModel<GiftCertificateModel> findWithFilter(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                @RequestBody GiftCertificateFilterCondition giftCertificateFilterCondition) {
        Page<GiftCertificateDto> giftCertificatePage = giftCertificateService.findWithFilter(page, size, giftCertificateFilterCondition);
        CollectionModel<GiftCertificateModel> collectionModel = giftCertificateAssembler.toCollectionModel(giftCertificatePage.getContent());
        return giftCertificateModelProcessor.process(giftCertificatePage, size, collectionModel);
    }

    @PatchMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        giftCertificateService.delete(id);
    }
}
