package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.assembler.TagModelAssembler;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.hateoas.processor.TagModelProcessor;
import com.epam.esm.service.TagService;
import com.epam.esm.service.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;
    private final TagModelProcessor tagModelProcessor;

    @Autowired
    public TagController(TagService tagService, TagModelAssembler tagModelAssembler, TagModelProcessor tagModelProcessor) {
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
        this.tagModelProcessor = tagModelProcessor;
    }

    @PostMapping
    public TagDto save(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<TagModel> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findAll(page, size);
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, size, collectionModel);
    }

    @GetMapping("/most-popular")
    public CollectionModel<TagModel> findMostPopularTags(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findMostPopularTags(page, size);
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, size, collectionModel);
    }

    @GetMapping("/count")
    public int countAll() {
        return tagService.countAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}