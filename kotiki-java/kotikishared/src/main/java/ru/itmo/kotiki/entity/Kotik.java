package ru.itmo.kotiki.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.itmo.kotiki.enums.KotikColor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kotik")
public class Kotik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kotik_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "race")
    private String race;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Owner owner;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private KotikColor color;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "KotikiFriends",
            joinColumns = @JoinColumn(name = "FirstKotikId", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "SecondKotikId", nullable = false, updatable = false))
    private List<Kotik> friendsList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public KotikColor getColor() {
        return color;
    }

    public void setColor(KotikColor color) {
        this.color = color;
    }

    public List<Kotik> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Kotik> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public String toString() {
        return "Kotik{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", owner=" + owner +
                ", color=" + color +
                ", friendsList=" + friendsList +
                '}';
    }

    public void addFriend(Kotik friend) {
        friendsList.add(friend);
    }
}