package ru.itmo.kotiki.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private final Long id;
    private final String username;

    private final String password;


    public UserDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDto(String username){
        this.username = username;
        this.id = null;
        this.password = null;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}