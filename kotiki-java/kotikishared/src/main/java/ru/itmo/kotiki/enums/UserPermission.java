package ru.itmo.kotiki.enums;

public enum UserPermission {

    OWNER_WRITE("owner:write"),
    OWNER_READ("owner:read"),
    KOTIKI_WRITE("kotiki:write"),
    KOTIKI_READ("kotiki:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}