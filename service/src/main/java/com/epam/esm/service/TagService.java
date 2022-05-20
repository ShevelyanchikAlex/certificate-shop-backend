package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

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
     * @return List of TagDto
     */
    List<TagDto> findAll();


    /**
     * Finds most popular TagDtos which are included in Certificates included in Orders
     *
     * @return List of TagDto
     */
    List<TagDto> findMostPopularTags();

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
