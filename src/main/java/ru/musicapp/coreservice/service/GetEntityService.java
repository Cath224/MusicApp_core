package ru.musicapp.coreservice.service;

import org.springframework.data.domain.Page;
import ru.musicapp.coreservice.model.QueryParams;

public interface GetEntityService<Dto, Id> {


    Dto getById(Id id);

    Page<Dto> get(QueryParams params);

}
