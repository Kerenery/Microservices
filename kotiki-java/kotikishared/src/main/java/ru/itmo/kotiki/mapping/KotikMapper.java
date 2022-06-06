package ru.itmo.kotiki.mapping;

import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.dto.KotikDtoBuilder;
import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.entity.Kotik;
import ru.itmo.kotiki.entity.KotikBuilder;
import ru.itmo.kotiki.entity.Owner;

public class KotikMapper {
    public static Kotik mapToEntity(KotikDto kotikDto){
        return KotikBuilder.aKotik()
                .withOwner(OwnerMapper.mapToEntity(kotikDto.getOwnerDto()))
                .withColor(kotikDto.getColor())
                .withName(kotikDto.getName())
                .withRace(kotikDto.getRace())
                .build();
    }

    public static KotikDto mapToDto(Kotik kotik){
        return KotikDtoBuilder.aKotikDto()
                .withId(kotik.getId())
                .withColor(kotik.getColor())
                .withName(kotik.getName())
                .withRace(kotik.getRace())
                .withOwnerDto(OwnerMapper.mapToDto(kotik.getOwner()))
                .build();
    }

}
