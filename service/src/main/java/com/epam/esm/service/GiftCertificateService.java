package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.service.pagination.Page;

import java.util.List;

/**
 * {@link GiftCertificateService} is an interface that contains all operations available for {@link GiftCertificateDto} of the API.
 */
public interface GiftCertificateService {
    /**
     * Saves GiftCertificateDto
     *
     * @param giftCertificateDto GiftCertificateDto
     * @return saved GiftCertificateDto
     */
    GiftCertificateDto save(GiftCertificateDto giftCertificateDto);

    /**
     * Finds GiftCertificateDto by id
     *
     * @param id if of GiftCertificateDto
     * @return GiftCertificateDto
     */
    GiftCertificateDto findById(Long id);

    /**
     * Finds all GiftCertificatesDto
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @return Page with GiftCertificatesDto
     */
    Page<GiftCertificateDto> findAll(Integer pageIndex, Integer size);

    /**
     * Finds GiftCertificatesDto by FilterCondition
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @param giftCertificateFilterCondition Filter Condition
     * @return Page with filtered GiftCertificatesDto
     */
    Page<GiftCertificateDto> findWithFilter(Integer pageIndex, Integer size, GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Updates GiftCertificateDto
     *
     * @param giftCertificateDto GiftCertificateDto
     * @return 1 if the update operation was successful, otherwise 0
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);

    /**
     * Deletes GiftCertificateDto by id
     *
     * @param id id of GiftCertificate
     */
    void delete(Long id);
}
