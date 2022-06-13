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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;
    private final TagModelProcessor tagModelProcessor;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public TagModel save(@RequestBody TagDto tagDto) {
        return tagModelAssembler.toModel(tagService.save(tagDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public TagModel findById(@PathVariable Long id) {
        return tagModelAssembler.toModel(tagService.findById(id));
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public CollectionModel<TagModel> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findAll(PageRequest.of(pageIndex, size));
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, collectionModel);
    }

    @GetMapping("/most-popular")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public CollectionModel<TagModel> findMostPopularTags(@RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<TagDto> tagsPage = tagService.findMostPopularTags(PageRequest.of(pageIndex, size));
        CollectionModel<TagModel> collectionModel = tagModelAssembler.toCollectionModel(tagsPage.getContent());
        return tagModelProcessor.process(tagsPage, collectionModel);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public int countAll() {
        return tagService.countAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}