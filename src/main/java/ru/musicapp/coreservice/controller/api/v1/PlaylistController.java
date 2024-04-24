package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.controller.api.CrudController;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistPatchDto;
import ru.musicapp.coreservice.service.CrudEntityService;
import ru.musicapp.coreservice.service.PlaylistService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/playlists")
@RequiredArgsConstructor
public class PlaylistController extends CrudController<PlaylistCreateDto, PlaylistPatchDto, PlaylistDto, UUID> {

    private final PlaylistService service;

    @PatchMapping("{id}/_songs")
    public void deleteSongs(@PathVariable UUID id, @RequestBody Set<UUID> songsIdsToDelete) {
        service.deleteSongs(id, songsIdsToDelete);
    }

    @Override
    protected CrudEntityService<PlaylistDto, PlaylistCreateDto, PlaylistPatchDto, UUID> getEntityService() {
        return service;
    }
}
