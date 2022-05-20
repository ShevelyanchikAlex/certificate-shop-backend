package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.List;

/**
 * {@link TagRepository} is an interface that contains all operations available for {@link Tag} of the API.
 */
public interface TagRepository extends CrudRepository<Tag> {
    /**
     * Counts all Tags
     *
     * @return count of Tags
     */
    int countAll();

    /**
     * Finds Tag with name
     *
     * @param name Name of Tag
     * @return Founded Tag
     */
    Tag findByName(String name);

    /**
     * Finds most popular Tags which are included in Certificates included in Orders
     *
     * @return List of Tags
     */
    List<Tag> findMostPopularTags();

    /**
     * Checks if there is a Tag with name
     *
     * @param name Name of Tag
     * @return true if exists, otherwise false
     */
    boolean existsTagByName(String name);
}
