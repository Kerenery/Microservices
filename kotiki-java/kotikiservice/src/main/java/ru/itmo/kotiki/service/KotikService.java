package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.entity.Kotik;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.enums.KotikColor;
import ru.itmo.kotiki.tool.ServiceException;

import java.util.List;

public interface KotikService {
    KotikDto add(KotikDto kotik);

    List<KotikDto> getAll(Long ownerId);

    KotikDto getById(long id) throws ServiceException;

    void update(KotikDto kotik) throws ServiceException;

    void remove(long id) throws ServiceException;

    void addFriend(long firstKotikId, long secondKotikId) throws ServiceException;

    List<KotikDto> findAllByRace(String race, Long ownerId);

    List<KotikDto> findAllByColor(KotikColor kotikColor, Long ownerId);
}