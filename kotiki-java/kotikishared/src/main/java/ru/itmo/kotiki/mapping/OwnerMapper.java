package ru.itmo.kotiki.mapping;

import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.dto.OwnerDtoBuilder;
import ru.itmo.kotiki.dto.UserDto;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.entity.OwnerBuilder;

public class OwnerMapper {
    public static Owner mapToEntity(OwnerDto ownerDto){
        return OwnerBuilder
                .anOwner()
                .withOwnerId(ownerDto.getId())
                .withFirstName(ownerDto.getFirstName())
                .withSecondName(ownerDto.getSecondName())
                .withLastName(ownerDto.getLastName())
                .withDateOfBirth(ownerDto.getDateOfBirth())
                .withAge(ownerDto.getAge())
                .withUser(UserMapper.mapToEntity(ownerDto.getUserDto()))
                .build();
    }

    public static OwnerDto mapToDto(Owner owner){
        return OwnerDtoBuilder
                .anOwnerDto()
                .withId(owner.getId())
                .withFirstName(owner.getFirstName())
                .withSecondName(owner.getSecondName())
                .withLastName(owner.getLastName())
                .withDateOfBirth(owner.getDateOfBirth())
                .withUserDto(UserMapper.mapToDto(owner.getUser()))
                .build();
        }

}
