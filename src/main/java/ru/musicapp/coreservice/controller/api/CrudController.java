package ru.musicapp.coreservice.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.service.CrudEntityService;

public abstract class CrudController<CreateDto, PatchDto, Dto, Id> extends GetController<Dto, Id> {


    @PostMapping
    @PreAuthorize("coreServicePermissionEvaluator.canModify(#this)")
    public Dto create(@RequestBody CreateDto dto) {
        return getEntityService().create(dto);
    }

    @PatchMapping("{id}")
    @PreAuthorize("coreServicePermissionEvaluator.canModify(#id, #this)")
    public Dto update(@PathVariable Id id, @RequestBody PatchDto dto) {
        return getEntityService().update(dto, id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("coreServicePermissionEvaluator.canModify(#this)")
    public void deleteById(@PathVariable Id id) {
        getEntityService().deleteById(id);
    }

    @Override
    protected abstract CrudEntityService<Dto, CreateDto, PatchDto, Id> getEntityService();
}
