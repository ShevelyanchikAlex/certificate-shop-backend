package com.epam.esm.domain;

import com.epam.esm.audit.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditListener.class)
public class Tag extends AbstractEntity {
    @Column(name = "name", nullable = false, length = 45, unique = true)
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
