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
     * @return id of saved tagDto
     */
    long save(TagDto tagDto);

    /**
     * Finds TagDto by id
     *
     * @param id id of TagDto
     * @return TagDto
     */
    TagDto findById(long id);

    /**
     * Finds all TagDtos
     *
     * @return List of TagDto
     */
    List<TagDto> findAll();

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
     * @return 1 if the update operation was successful, otherwise 0
     */
    int delete(long id);
}
