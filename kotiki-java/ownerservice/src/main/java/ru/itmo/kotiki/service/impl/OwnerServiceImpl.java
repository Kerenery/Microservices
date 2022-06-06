package ru.itmo.kotiki.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.dto.OwnerDtoBuilder;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.entity.OwnerBuilder;
import ru.itmo.kotiki.mapping.OwnerMapper;
import ru.itmo.kotiki.repository.OwnerRepository;
import ru.itmo.kotiki.service.OwnerService;
import ru.itmo.kotiki.tool.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itmo.kotiki.mapping.OwnerMapper.mapToDto;
import static ru.itmo.kotiki.mapping.OwnerMapper.mapToEntity;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerDto add(OwnerDto ownerDto) {
        var owner = mapToEntity(ownerDto);
        return mapToDto(ownerRepository.save(owner));
    }

    @Override
    public List<OwnerDto> getAll() {
        return mapListToDto(ownerRepository.findAll());
    }

    @Override
    public OwnerDto getById(long id) throws ServiceException {
        var ownerEntity = ownerRepository.findById(id);

        if (ownerEntity.isEmpty()){
            throw new ServiceException("such owner does not exist");
        }

        return mapToDto(ownerEntity.get());
    }

    @Override
    public void update(OwnerDto owner) throws ServiceException {
        var ownerEntityOpt = ownerRepository.findById(owner.getId());

        if (ownerEntityOpt.isEmpty()){
            throw new ServiceException("such owner does not exist");
        }

        var ownerEntity = ownerEntityOpt.get();
        ownerEntity = mapToEntity(owner);
        ownerRepository.save(ownerEntity);
    }

    @Override
    public void remove(long id) throws ServiceException {
        var ownerEntity = ownerRepository.findById(id);

        if (ownerEntity.isEmpty()){
            throw new ServiceException("such owner does not exist");
        }

        ownerRepository.deleteById(id);
    }

    private List<OwnerDto> mapListToDto(List<Owner> owners) {
        return owners
                .stream()
                .map(OwnerMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
