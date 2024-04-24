package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.mapper.MusicianMapper;
import ru.musicapp.coreservice.model.dto.music.MusicianCreateDto;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.MusicianPatchDto;
import ru.musicapp.coreservice.model.entity.music.Musician;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.MusicianRepository;
import ru.musicapp.coreservice.service.MusicianService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MusicianServiceImpl extends CrudEntityServiceImpl<Musician, MusicianDto, MusicianCreateDto, MusicianPatchDto, UUID> implements MusicianService {

    private final MusicianRepository repository;
    private final MusicianMapper mapper;

    @Override
    protected EntityRepository<Musician, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Musician, MusicianDto, MusicianCreateDto, MusicianPatchDto> getMapper() {
        return mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MusicianDto> findSimilarMusicians(UUID musicianId) {
        return mapper.toDtos(repository.findAllSimilarById(musicianId));
    }
}
