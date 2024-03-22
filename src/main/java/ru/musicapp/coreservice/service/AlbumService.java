package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;

import java.util.UUID;

public interface AlbumService extends CrudEntityService<AlbumDto, AlbumCreateDto, AlbumPatchDto, UUID> {
}
