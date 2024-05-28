package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.SongShortMapper;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;
import ru.musicapp.coreservice.repository.SongRepository;
import ru.musicapp.coreservice.service.MusicianTopSongService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MusicianTopSongServiceImpl implements MusicianTopSongService {

    private final SongRepository songRepository;
    private final SongShortMapper songShortMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SongShortDto> get(UUID musicianId) {
        Set<UUID> songs = songRepository.findTopIds5ByMusicianId(musicianId);
        return songShortMapper.toDtos(songRepository.findAllById(songs));
    }
}
