package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.mapper.MusicianMapper;
import ru.musicapp.coreservice.mapper.MusicianShortMapper;
import ru.musicapp.coreservice.model.dto.music.*;
import ru.musicapp.coreservice.model.entity.music.Musician;
import ru.musicapp.coreservice.model.type.ElementType;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.MusicianRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.MusicianService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicianServiceImpl extends CrudHistoryEntityServiceImpl<Musician, MusicianDto, MusicianCreateDto, MusicianPatchDto, UUID> implements MusicianService {

    private final MusicianRepository repository;
    private final MusicianMapper mapper;
    private final MusicianShortMapper shortMapper;

    @Override
    protected EntityRepository<Musician, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Musician, MusicianDto, MusicianCreateDto, MusicianPatchDto> getMapper() {
        return mapper;
    }

    @Transactional
    @Cacheable(cacheNames = "musician", key = "#uuid")
    @Override
    public MusicianDto getById(UUID uuid) {
        return super.getById(uuid);
    }

    @Override
    protected void saveToClickHistory(UUID id) {
        clickHistoryService.saveMusicianClick(id, SecurityContextFacade.get().getId());
    }

    @Override
    protected void saveSearchToClickHistory(Collection<MusicianDto> musicianDtos) {
        if (CollectionUtils.isEmpty(musicianDtos)) {
            return;
        }
        EnumMap<ElementType, Set<String>> enumMap = new EnumMap<>(Map.of(ElementType.MUSICIAN,
                musicianDtos.stream().map((el) -> String.valueOf(el.getId())).collect(Collectors.toSet())));
        clickHistoryService.saveSearch(enumMap, SecurityContextFacade.get().getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<MusicianDto> findSimilarMusicians(UUID musicianId) {
        return mapper.toDtos(repository.findAllSimilarById(musicianId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<MusicianShortDto> getShort() {
        return shortMapper.toDtos(repository.findAll());
    }
}
