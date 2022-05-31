package com.epam.esm.hateoas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagModel extends RepresentationModel<TagModel> {
    private long id;
    private String name;
}
