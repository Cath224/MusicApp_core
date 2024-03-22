package ru.musicapp.coreservice.mapper;

import java.util.Collection;
import java.util.List;

public interface EntityMapper<E, Dto> {

    E toEntity(Dto dto);

    Dto toDto(E entity);

    List<E> toEntities(Collection<Dto> dtos);

    List<Dto> toDtos(Collection<E> entities);
}
