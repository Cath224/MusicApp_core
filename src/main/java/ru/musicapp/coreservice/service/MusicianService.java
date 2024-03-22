package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.MusicianCreateDto;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.MusicianPatchDto;

import java.util.UUID;

public interface MusicianService extends CrudEntityService<MusicianDto, MusicianCreateDto, MusicianPatchDto, UUID> {
}
