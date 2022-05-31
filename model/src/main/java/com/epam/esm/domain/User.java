package com.epam.esm.domain;

import com.epam.esm.audit.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditListener.class)
public class User extends AbstractEntity {
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User(long id, String name, List<Order> orders) {
        super(id);
        this.name = name;
        this.orders = orders;
    }
}
