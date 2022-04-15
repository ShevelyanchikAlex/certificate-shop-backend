package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

public interface TagRepository extends CrudRepository<Tag>{
    Tag findByName(String name);
}
