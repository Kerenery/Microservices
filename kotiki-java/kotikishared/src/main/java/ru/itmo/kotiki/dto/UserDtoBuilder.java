package ru.itmo.kotiki.dto;

public final class UserDtoBuilder {
    private Long id;
    private String username;
    private String password;

    private UserDtoBuilder() {
    }

    public static UserDtoBuilder anUserDto() {
        return new UserDtoBuilder();
    }

    public UserDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDtoBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDtoBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDto build() {
        return id == null ? new UserDto(username)
                : new UserDto(id, username, password);
    }
}