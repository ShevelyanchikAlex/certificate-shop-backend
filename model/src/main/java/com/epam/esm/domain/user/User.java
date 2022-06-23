package com.epam.esm.domain.user;

import com.epam.esm.audit.AuditListener;
import com.epam.esm.domain.AbstractEntity;
import com.epam.esm.domain.Order;
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

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User(long id, String name, String email, String password, Role role, Status status, List<Order> orders) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.orders = orders;
    }
}
