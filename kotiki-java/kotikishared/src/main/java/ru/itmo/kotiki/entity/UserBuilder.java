package ru.itmo.kotiki.entity;

import org.apache.tomcat.jni.Local;
import ru.itmo.kotiki.enums.UserRole;

import java.time.LocalDate;

public final class UserBuilder {
    private UserRole role;
    private String password;
    private String username;

    private Long id;

    private LocalDate credentials;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withCredentials(LocalDate credentials){
        this.credentials = credentials;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        user.setPassword(password);
        user.setUsername(username);
        user.setCredentialsDate(credentials == null
                ? LocalDate.now().plusYears(1)
                : credentials);
        return user;
    }
}