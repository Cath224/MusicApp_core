package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;

import java.util.List;

public interface TopsService {

    List<SongDto> getNewestTop10Songs();

    List<SongDto> getTop10Songs();

    List<MusicianDto> getTop10Musicians();

}
