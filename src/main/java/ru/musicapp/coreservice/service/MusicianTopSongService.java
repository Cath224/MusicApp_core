package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.SongShortDto;

import java.util.List;
import java.util.UUID;

public interface MusicianTopSongService {

    List<SongShortDto> get(UUID musicianId);

}
