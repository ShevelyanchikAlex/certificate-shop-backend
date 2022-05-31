package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.assembler.TagModelAssembler;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.hateoas.processor.TagModelProcessor;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;
    private final TagModelProcessor tagModelProcessor;

    @PostMapping
    public TagDto save(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping(produces = "application/json")
    public CollectionModel<TagModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findAll(PageRequest.of(pageIndex, size));
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, collectionModel);
    }

    @GetMapping("/most-popular")
    public CollectionModel<TagModel> findMostPopularTags(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findMostPopularTags(PageRequest.of(pageIndex, size));
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, collectionModel);
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