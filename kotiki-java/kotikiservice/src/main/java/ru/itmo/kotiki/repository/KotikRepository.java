package ru.itmo.kotiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.entity.Kotik;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.enums.KotikColor;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface KotikRepository extends JpaRepository<Kotik, Long> {
    List<Kotik> findAllByColorAndOwner_Id(KotikColor color, Long ownerId);
    List<Kotik> findAllByRaceLikeAndOwner_Id(String race, Long ownerId);
    List<Kotik> findAllByOwnerEquals(Owner owner);
    List<Kotik> findAllByOwner(Owner owner);
    List<Kotik> existsByOwnerEquals(Owner owner);
    List<Kotik> findAllByOwner_Id(Long ownerId);
}
