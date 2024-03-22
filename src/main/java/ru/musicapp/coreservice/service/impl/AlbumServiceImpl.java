package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.musicapp.coreservice.mapper.AlbumMapper;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;
import ru.musicapp.coreservice.model.entity.music.Album;
import ru.musicapp.coreservice.repository.AlbumRepository;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.service.AlbumService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl extends CrudEntityServiceImpl<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto, UUID> implements AlbumService {

    private final AlbumRepository repository;
    private final AlbumMapper mapper;

    @Override
    protected EntityRepository<Album, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto> getMapper() {
        return mapper;
    }
}
