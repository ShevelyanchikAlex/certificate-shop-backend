package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.FilterCondition;
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
    public long save(@RequestBody GiftCertificateDto giftCertificateDto) {
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
    public List<GiftCertificateDto> findWithFilter(
            @RequestBody FilterCondition filterCondition) {
        return giftCertificateService.findWithFilter(filterCondition);
    }

    @PatchMapping
    public long update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable long id) {
        return giftCertificateService.delete(id);
    }
}
