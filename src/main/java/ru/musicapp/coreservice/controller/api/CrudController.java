package ru.musicapp.coreservice.controller.api;

import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.service.CrudEntityService;

public abstract class CrudController<CreateDto, PatchDto, Dto, Id> extends GetController<Dto, Id> {

    @PostMapping
    public Dto create(@RequestBody CreateDto dto) {
        return getEntityService().create(dto);
    }

    @PatchMapping("{id}")
    public Dto update(@PathVariable Id id, @RequestBody PatchDto dto) {
        return getEntityService().update(dto, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Id id) {
        getEntityService().deleteById(id);
    }

    @Override
    protected abstract CrudEntityService<Dto, CreateDto, PatchDto, Id> getEntityService();
}
