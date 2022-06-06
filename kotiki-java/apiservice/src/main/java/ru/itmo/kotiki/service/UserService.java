package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dto.RegistrationPayload;
import ru.itmo.kotiki.dto.UserDto;
import ru.itmo.kotiki.enums.UserRole;

import java.util.List;

public interface UserService {
    UserDto addUser(RegistrationPayload registrationPayload);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    void updateUser(UserDto userDto);
    void removeUser(Long id);

    void updateRole(Long userIdToBeUpdated, UserRole role);
}