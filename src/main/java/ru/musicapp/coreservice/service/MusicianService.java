package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.MusicianCreateDto;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.MusicianPatchDto;
import ru.musicapp.coreservice.model.dto.music.MusicianShortDto;

import java.util.List;
import java.util.UUID;

public interface MusicianService extends CrudEntityService<MusicianDto, MusicianCreateDto, MusicianPatchDto, UUID> {

    List<MusicianDto> findSimilarMusicians(UUID musicianId);

    List<MusicianShortDto> getShort();

}
