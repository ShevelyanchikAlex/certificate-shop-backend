package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable Pageable
     * @return Page with GiftCertificatesDto
     */
    Page<GiftCertificateDto> findAll(Pageable pageable);

    /**
     * Finds GiftCertificatesDto by FilterCondition
     *
     * @param pageable                       Pageable
     * @param giftCertificateFilterCondition Filter Condition
     * @return Page with filtered GiftCertificatesDto
     */
    Page<GiftCertificateDto> findWithFilter(Pageable pageable, GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Get count of all GiftCertificates
     *
     * @return Long count of all GiftCertificates
     */
    Long getGiftCertificatesCount();

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
