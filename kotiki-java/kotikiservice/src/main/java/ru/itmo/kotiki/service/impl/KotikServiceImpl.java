package ru.itmo.kotiki.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.dto.KotikDtoBuilder;
import ru.itmo.kotiki.entity.Kotik;
import ru.itmo.kotiki.entity.KotikBuilder;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.enums.KotikColor;
import ru.itmo.kotiki.repository.KotikRepository;
import ru.itmo.kotiki.service.KotikService;
import ru.itmo.kotiki.tool.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itmo.kotiki.mapping.KotikMapper.*;

@Service
public class KotikServiceImpl implements KotikService {

    private final KotikRepository kotikRepository;

    @Autowired
    public KotikServiceImpl(KotikRepository kotikRepository,
                            RabbitTemplate rabbitTemplate) {
        this.kotikRepository = kotikRepository;
    }


    @Override
    public KotikDto add(KotikDto kotikDto) {
        var kotik = mapToEntity(kotikDto);
        return mapToDto(kotikRepository.save(kotik));
    }

    @Override
    public List<KotikDto> getAll(Long ownerId) {
        return mapList(kotikRepository.
                findAllByOwner_Id(ownerId));
    }

    @Override
    public KotikDto getById(long id) throws ServiceException {
        var kotikEntity = kotikRepository.findById(id);

        if (kotikEntity.isEmpty()){
            throw new ServiceException("such kotik does not exist");
        }

        return mapToDto(kotikEntity.get());
    }

    @Override
    public void update(KotikDto kotikDto) throws ServiceException {
        var kotikEntity = kotikRepository.findById(kotikDto.getId());

        if (kotikEntity.isEmpty()){
            throw new ServiceException("such kotik does not exist");
        }

        kotikRepository.save(kotikEntity.get());
    }

    @Override
    public void remove(long id) throws ServiceException {
        var kotikEntity = kotikRepository.findById(id);

        if (kotikEntity.isEmpty()){
            throw new ServiceException("such kotik does not exist");
        }

        kotikRepository.deleteById(id);
    }

    @Override
    public void addFriend(long firstKotikId, long secondKotikId) throws ServiceException {
        var firstKotikEntity = kotikRepository.findById(firstKotikId);
        var secondKotikEntity = kotikRepository.findById(secondKotikId);

        if (firstKotikEntity.isEmpty() || secondKotikEntity.isEmpty()){
            throw new ServiceException("one of kotiks does not exist");
        }

        firstKotikEntity.get().addFriend(secondKotikEntity.get());
        secondKotikEntity.get().addFriend(firstKotikEntity.get());
        kotikRepository.save(firstKotikEntity.get());
        kotikRepository.save(secondKotikEntity.get());
    }

    @Override
    public List<KotikDto> findAllByRace(String race, Long ownerId) {
        return mapList(kotikRepository
                .findAllByRaceLikeAndOwner_Id(race, ownerId));
    }

    @Override
    public List<KotikDto> findAllByColor(KotikColor kotikColor, Long ownerId) {
        return mapList(kotikRepository
                .findAllByColorAndOwner_Id(kotikColor, ownerId));
    }


    private List<KotikDto> mapList(List<Kotik> kotiks) {
        return kotiks
                .stream()
                .map(kotik -> mapToDto(kotik))
                .collect(Collectors.toList());
    }
}