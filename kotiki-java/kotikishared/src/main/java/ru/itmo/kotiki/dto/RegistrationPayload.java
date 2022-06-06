package ru.itmo.kotiki.dto;

public class RegistrationPayload {
    private String username;
    private String password;

    public RegistrationPayload(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}