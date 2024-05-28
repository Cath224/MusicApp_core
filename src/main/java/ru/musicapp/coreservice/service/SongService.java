package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.SongCreateDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.model.dto.music.SongPatchDto;

import java.util.UUID;

public interface SongService extends CrudEntityService<SongDto, SongCreateDto, SongPatchDto, UUID> {

}
