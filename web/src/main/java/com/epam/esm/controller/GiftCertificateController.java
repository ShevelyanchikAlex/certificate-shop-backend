package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
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
    public List<GiftCertificateDto> findAll() {
        return giftCertificateService.findAll();
    }

    @GetMapping("/filter")
    public List<GiftCertificateDto> findWithFilter(@RequestBody GiftCertificateFilterCondition giftCertificateFilterCondition) {
        return giftCertificateService.findWithFilter(giftCertificateFilterCondition);
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
