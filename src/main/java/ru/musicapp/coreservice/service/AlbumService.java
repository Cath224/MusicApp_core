package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;
import ru.musicapp.coreservice.model.dto.music.AlbumShortDto;

import java.util.List;
import java.util.UUID;

public interface AlbumService extends CrudEntityService<AlbumDto, AlbumCreateDto, AlbumPatchDto, UUID> {

    List<AlbumShortDto> getShort();

}
