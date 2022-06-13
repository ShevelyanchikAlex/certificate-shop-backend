package com.epam.esm.domain;

import lombok.Getter;

@Getter
public enum Permission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write");

    private final String name;

    Permission(String name) {
        this.name = name;
    }
}
