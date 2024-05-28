package ru.musicapp.coreservice.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.musicapp.coreservice.model.PageResponse;
import ru.musicapp.coreservice.model.QueryParams;
import ru.musicapp.coreservice.service.GetEntityService;

public abstract class GetController<Dto, Id> {


    @GetMapping("{id}")
    @PreAuthorize("coreServicePermissionEvaluator.hasAccess(#id, #this)")
    public Dto getById(@PathVariable Id id) {
        return getEntityService().getById(id);
    }

    @GetMapping
    @PreAuthorize("coreServicePermissionEvaluator.hasAccess(#this)")
    public PageResponse<Dto> get(QueryParams params) {
        return PageResponse.of(getEntityService().get(params));
    }

    protected abstract GetEntityService<Dto, Id> getEntityService();

}
