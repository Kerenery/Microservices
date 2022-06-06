package ru.itmo.kotiki.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.kotiki.enums.KotikColor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class KotikDto implements Serializable {
    private long id;
    private String name;
    private String race;
    private OwnerDto ownerDto;
    private KotikColor color;

    public KotikDto(long id,
                    String name,
                    String race,
                    OwnerDto ownerDto,
                    KotikColor color) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.ownerDto = ownerDto;
        this.color = color;
    }

    @JsonCreator
    public KotikDto(@JsonProperty("name")
                    String name,
                    @JsonProperty("race")
                    String race,
                    @JsonProperty("ownerDto")
                        OwnerDto ownerDto,
                    @JsonProperty("color")
                    KotikColor color) {
        this.name = name;
        this.race = race;
        this.ownerDto = ownerDto;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public KotikColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "KotikDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", ownerDto=" + ownerDto +
                ", color=" + color +
                '}';
    }

}