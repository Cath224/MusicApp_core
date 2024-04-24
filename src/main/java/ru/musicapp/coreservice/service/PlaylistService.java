package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.playlist.PlaylistCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistPatchDto;

import java.util.Set;
import java.util.UUID;

public interface PlaylistService extends CrudEntityService<PlaylistDto, PlaylistCreateDto, PlaylistPatchDto, UUID> {

    void deleteSongs(UUID id, Set<UUID> songsIdsToDelete);

}
