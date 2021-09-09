package com.example.APIProductosSpringSecurityConUsuariosPersistentes.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    ADMIN(Set.of(
            UserPermission.USER_WRITE, UserPermission.USER_READ, UserPermission.PRODUCTO_READ, UserPermission.PRODUCTO_WRITE
    )),
    CLIENTE(Set.of(
            UserPermission.USER_READ,UserPermission.PRODUCTO_READ
    ));

    private Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public String getRole(){
        return this.name();
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permisos = getPermissions().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());
         permisos.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
         return permisos;

    }
}
