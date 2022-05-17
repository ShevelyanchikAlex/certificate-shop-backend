package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

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
     * Checks if there is a Tag with name
     *
     * @param name Name of Tag
     * @return true if exists, otherwise false
     */
    boolean existsTagByName(String name);
}
