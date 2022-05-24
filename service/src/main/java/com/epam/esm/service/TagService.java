package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.pagination.Page;

/**
 * {@link TagService} is an interface that contains all operations available for {@link TagDto} of the API.
 */
public interface TagService {
    /**
     * Saves {@link TagDto} to data source
     *
     * @param tagDto TagDto
     * @return saved tagDto
     */
    TagDto save(TagDto tagDto);

    /**
     * Finds TagDto by id
     *
     * @param id id of TagDto
     * @return TagDto
     */
    TagDto findById(Long id);

    /**
     * Finds all TagDtos
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @return List of found TagDtos
     */
    Page<TagDto> findAll(Integer pageIndex, Integer size);

    /**
     * Finds most popular TagDtos which are included in Certificates included in Orders
     *
     * @param pageIndex Number of Page
     * @param size Size of Page
     * @return List of TagDto
     */
    Page<TagDto> findMostPopularTags(Integer pageIndex, Integer size);

    /**
     * Counts all TagDtos
     *
     * @return count of TagDtos
     */
    int countAll();

    /**
     * Deletes TagDto by id
     *
     * @param id id Of TagDto
     */
    void delete(Long id);
}
