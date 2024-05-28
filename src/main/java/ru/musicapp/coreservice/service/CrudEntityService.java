package ru.musicapp.coreservice.service;

public interface CrudEntityService<Dto, CreateDto, PatchDto, Id> extends GetEntityService<Dto, Id> {

    Dto create(CreateDto createDto);

    Dto update(PatchDto patchDto, Id id);

    void deleteById(Id id);

}
