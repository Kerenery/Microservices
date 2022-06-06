package ru.itmo.kotiki.dto;

import ru.itmo.kotiki.enums.KotikColor;

public final class KotikDtoBuilder {
    private long id;
    private String name;
    private String race;
    private OwnerDto ownerDto;
    private KotikColor color;

    private KotikDtoBuilder() {
    }

    public static KotikDtoBuilder aKotikDto() {
        return new KotikDtoBuilder();
    }

    public KotikDtoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public KotikDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public KotikDtoBuilder withRace(String race) {
        this.race = race;
        return this;
    }

    public KotikDtoBuilder withOwnerDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
        return this;
    }

    public KotikDtoBuilder withColor(KotikColor color) {
        this.color = color;
        return this;
    }

    public KotikDto build() {
        return id == 0 ? new KotikDto(name, race, ownerDto, color)
                : new KotikDto(id, name, race, ownerDto, color) ;
    }
}
