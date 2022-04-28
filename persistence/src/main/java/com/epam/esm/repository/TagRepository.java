package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.Set;

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
     * Finds all Tags with id of GiftsCertificate
     *
     * @param giftCertificateId id of GiftsCertificate
     * @return Set of founded Tags
     */
    Set<Tag> findAllByGiftCertificateId(long giftCertificateId);

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
