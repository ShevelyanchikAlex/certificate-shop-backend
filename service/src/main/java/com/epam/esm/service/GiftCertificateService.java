package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;

import java.util.List;

/**
 * {@link GiftCertificateService} is an interface that contains all operations available for {@link GiftCertificateDto} of the API.
 */
public interface GiftCertificateService {
    /**
     * Saves GiftCertificateDto
     *
     * @param giftCertificateDto GiftCertificateDto
     * @return id of saved GiftCertificateDto
     */
    long save(GiftCertificateDto giftCertificateDto);

    /**
     * Finds GiftCertificateDto by id
     *
     * @param id if of GiftCertificateDto
     * @return GiftCertificateDto
     */
    GiftCertificateDto findById(long id);

    /**
     * Finds all GiftCertificateDtos
     *
     * @return List of all GiftCertificateDtos
     */
    List<GiftCertificateDto> findAll();

    /**
     * Finds GiftCertificateDtos by {@link GiftCertificateFilterCondition}
     *
     * @param giftCertificateFilterCondition Condition for filter according to specific parameters
     * @return List with filtered GiftCertificateDtos
     */
    List<GiftCertificateDto> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition);

    /**
     * Updates GiftCertificateDto
     *
     * @param giftCertificateDto GiftCertificateDto
     * @return 1 if the update operation was successful, otherwise 0
     */
    int update(GiftCertificateDto giftCertificateDto);

    /**
     * Deletes GiftCertificateDto by id
     *
     * @param id id of GiftCertificate
     * @return 1 if the update operation was successful, otherwise 0
     */
    int delete(long id);
}
