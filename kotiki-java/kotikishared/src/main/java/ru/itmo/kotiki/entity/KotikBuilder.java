package ru.itmo.kotiki.entity;

import ru.itmo.kotiki.enums.KotikColor;

import java.util.ArrayList;
import java.util.List;

public final class KotikBuilder {
    private String name;
    private String race;
    private Owner owner;
    private KotikColor color;
    private List<Kotik> friendsList = new ArrayList<>();

    private KotikBuilder() {
    }

    public static KotikBuilder aKotik() {
        return new KotikBuilder();
    }

    public KotikBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public KotikBuilder withRace(String race) {
        this.race = race;
        return this;
    }

    public KotikBuilder withOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public KotikBuilder withColor(KotikColor color) {
        this.color = color;
        return this;
    }

    public KotikBuilder withFriendsList(List<Kotik> friendsList) {
        this.friendsList = friendsList;
        return this;
    }

    public Kotik build() {
        Kotik kotik = new Kotik();
        kotik.setName(name);
        kotik.setRace(race);
        kotik.setOwner(owner);
        kotik.setColor(color);
        kotik.setFriendsList(friendsList);
        return kotik;
    }
}