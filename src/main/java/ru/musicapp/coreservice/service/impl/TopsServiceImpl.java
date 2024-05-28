package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.MusicianMapper;
import ru.musicapp.coreservice.mapper.SongMapper;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.repository.MusicianRepository;
import ru.musicapp.coreservice.repository.SongRepository;
import ru.musicapp.coreservice.service.TopsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopsServiceImpl implements TopsService {

    private final SongRepository songRepository;
    private final MusicianRepository musicianRepository;
    private final SongMapper songMapper;
    private final MusicianMapper musicianMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SongDto> getNewestTop10Songs() {
        return songMapper.toDtos(songRepository.findAllTop10SongsNewest());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SongDto> getTop10Songs() {
        return songMapper.toDtos(songRepository.findAllTop10Songs());
    }

    @Transactional(readOnly = true)
    @Override
    public List<MusicianDto> getTop10Musicians() {
        return musicianMapper.toDtos(musicianRepository.findAllTop10());
    }
}
