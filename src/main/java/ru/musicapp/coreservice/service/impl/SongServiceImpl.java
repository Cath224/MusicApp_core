package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.mapper.SongMapper;
import ru.musicapp.coreservice.model.dto.music.SongCreateDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.model.dto.music.SongPatchDto;
import ru.musicapp.coreservice.model.entity.music.Song;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.SongRepository;
import ru.musicapp.coreservice.service.SongService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongServiceImpl extends CrudEntityServiceImpl<Song, SongDto, SongCreateDto, SongPatchDto, UUID> implements SongService {

    private final SongRepository repository;
    private final SongMapper mapper;

    @Override
    protected EntityRepository<Song, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Song, SongDto, SongCreateDto, SongPatchDto> getMapper() {
        return mapper;
    }
}
