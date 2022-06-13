package com.epam.esm.domain.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    GUEST(Set.of(Permission.GUEST_PERMISSION)),
    USER(Set.of(Permission.GUEST_PERMISSION, Permission.USER_PERMISSION)),
    ADMIN(Set.of(Permission.GUEST_PERMISSION, Permission.USER_PERMISSION, Permission.ADMIN_PERMISSION));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }
}
