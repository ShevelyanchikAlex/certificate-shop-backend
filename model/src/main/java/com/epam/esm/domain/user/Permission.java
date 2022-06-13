package com.epam.esm.domain.user;

import lombok.Getter;

@Getter
public enum Permission {
    USER_PERMISSION("USER_PERMISSION"),
    ADMIN_PERMISSION("ADMIN_PERMISSION");

    private final String name;

    Permission(String name) {
        this.name = name;
    }
}
