package se.karingotrafiken.timemanager.rest.appmodel.common;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_BOAT_MANAGER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}