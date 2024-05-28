package ru.musicapp.coreservice.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface CrudEntityMapper<E, Dto, CreateDto, PatchDto> extends EntityMapper<E, Dto> {

    E fromCreateDto(CreateDto createDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E partialUpdate(@MappingTarget E entity, PatchDto updateDto);

}
