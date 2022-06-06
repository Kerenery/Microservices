package ru.itmo.kotiki.dto;

import java.time.LocalDate;

public final class OwnerDtoBuilder {
    private long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate dateOfBirth;
    private UserDto userDto;

    private OwnerDtoBuilder() {
    }

    public static OwnerDtoBuilder anOwnerDto() {
        return new OwnerDtoBuilder();
    }

    public OwnerDtoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public OwnerDtoBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OwnerDtoBuilder withSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public OwnerDtoBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OwnerDtoBuilder withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public OwnerDtoBuilder withUserDto(UserDto userDto) {
        this.userDto = userDto;
        return this;
    }

    public OwnerDto build() {
        return id == 0 ? new OwnerDto(firstName, secondName, lastName, dateOfBirth, userDto)
                : new OwnerDto(id, firstName, secondName, lastName, dateOfBirth, userDto);
    }
}
