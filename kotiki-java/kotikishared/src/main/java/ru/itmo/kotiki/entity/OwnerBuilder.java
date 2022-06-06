package ru.itmo.kotiki.entity;

import java.time.LocalDate;

public final class OwnerBuilder {
    private Long ownerId;
    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int age;

    private User user;

    private OwnerBuilder() {
    }

    public static OwnerBuilder anOwner() {
        return new OwnerBuilder();
    }

    public OwnerBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OwnerBuilder withSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public OwnerBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OwnerBuilder withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public OwnerBuilder withOwnerId(Long ownerId){
        this.ownerId = ownerId;
        return this;
    }

    public OwnerBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public OwnerBuilder withUser(User user){
        this.user = user;
        return this;
    }

    public Owner build() {
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setFirstName(firstName);
        owner.setSecondName(secondName);
        owner.setLastName(lastName);
        owner.setDateOfBirth(dateOfBirth);
        owner.setAge(age);
        owner.setUser(user);
        return owner;
    }
}