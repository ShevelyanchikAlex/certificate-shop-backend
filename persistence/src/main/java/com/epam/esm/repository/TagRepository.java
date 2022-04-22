package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.Set;

public interface TagRepository extends CrudRepository<Tag> {
    int countAll();

    Set<Tag> findAllByGiftCertificateId(long giftCertificateId);

    Tag findByName(String name);
}
