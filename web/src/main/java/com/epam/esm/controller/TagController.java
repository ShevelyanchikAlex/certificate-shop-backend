package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public TagDto save(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @GetMapping(produces = "application/json")
    public List<TagDto> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/count")
    public int countAll() {
        return tagService.countAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        tagService.delete(id);
    }
}