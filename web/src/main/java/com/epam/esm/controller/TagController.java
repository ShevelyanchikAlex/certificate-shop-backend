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
    public long save(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @GetMapping("/count")
    public int findByName() {
        return tagService.countAll();
    }

    @GetMapping(produces = "application/json")
    public List<TagDto> findAll() {
        return tagService.findAll();
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable long id) {
        return tagService.delete(id);
    }
}