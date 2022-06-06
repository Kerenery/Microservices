package ru.itmo.kotiki.mapping;

import ru.itmo.kotiki.dto.UserDto;
import ru.itmo.kotiki.dto.UserDtoBuilder;
import ru.itmo.kotiki.entity.User;
import ru.itmo.kotiki.entity.UserBuilder;

public class UserMapper {
    public static User mapToEntity(UserDto userDto){
        return UserBuilder.anUser()
                .withId(userDto.getId())
                .withUsername(userDto.getUsername())
                .withPassword(userDto.getPassword())
                .build();
    }
    public static UserDto mapToDto(User user){
        return UserDtoBuilder.anUserDto()
                .withUsername(user.getUsername()).build();
    }
}
