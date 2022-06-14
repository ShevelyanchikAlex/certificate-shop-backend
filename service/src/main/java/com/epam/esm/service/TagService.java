package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Finds all TagsDto
     *
     * @param pageable Pageable
     * @return List of found TagsDto
     */
    Page<TagDto> findAll(Pageable pageable);

    /**
     * Finds most popular TagsDto which are included in Certificates included in Orders
     *
     * @param pageable Pageable
     * @return List of TagDto
     */
    Page<TagDto> findMostPopularTags(Pageable pageable);

    /**
     * Deletes TagDto by id
     *
     * @param id id Of TagDto
     */
    void delete(Long id);
}
