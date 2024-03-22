package ru.musicapp.coreservice.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.service.CrudEntityService;

public abstract class CrudEntityServiceImpl<E, Dto, CreateDto, PatchDto, Id>
        extends GetEntityServiceImpl<E, Dto, Id>
        implements CrudEntityService<Dto, CreateDto, PatchDto, Id> {

    @Transactional
    @Override
    public Dto create(CreateDto createDto) {
        E entity = getMapper().fromCreateDto(createDto);
        entity = getRepository().save(entity);
        return getMapper().toDto(entity);
    }

    @Transactional
    @Override
    public Dto update(PatchDto patchDto, Id id) {
        E entity = getByIdInternal(id);
        entity = getMapper().partialUpdate(entity, patchDto);
        entity = getRepository().save(entity);
        return getMapper().toDto(entity);
    }

    @Transactional
    @Override
    public void deleteById(Id id) {
        getRepository().deleteById(id);
    }

    @Override
    protected abstract CrudEntityMapper<E, Dto, CreateDto, PatchDto> getMapper();
}
