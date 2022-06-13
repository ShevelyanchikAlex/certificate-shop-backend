package com.epam.esm.domain.user;

import lombok.Getter;

@Getter
public enum Permission {
    GUEST_PERMISSION("GUEST_PERMISSION"),
    USER_PERMISSION("USER_PERMISSION"),
    ADMIN_PERMISSION("ADMIN_PERMISSION");

    private final String name;

    Permission(String name) {
        this.name = name;
    }
}
