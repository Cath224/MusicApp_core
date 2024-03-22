package ru.musicapp.coreservice.service.impl;

import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.EntityMapper;
import ru.musicapp.coreservice.model.QueryParams;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.service.GetEntityService;

public abstract class GetEntityServiceImpl<E, Dto, Id>
        implements GetEntityService<Dto, Id> {


    @Transactional(readOnly = true)
    @Override
    public Dto getById(Id id) {
        E entity = getByIdInternal(id);
        return getMapper().toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Dto> get(QueryParams params) {
        Page<E> result = getRepository().findAll(RSQLJPASupport.rsql(params.getQuery()), params.getPageRequest());
        return new PageImpl<>(
                getMapper().toDtos(result.getContent()),
                result.getPageable(),
                result.getTotalElements()
        );
    }

    protected E getByIdInternal(Id id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    protected abstract EntityRepository<E, Id> getRepository();


    protected abstract EntityMapper<E, Dto> getMapper();
}
