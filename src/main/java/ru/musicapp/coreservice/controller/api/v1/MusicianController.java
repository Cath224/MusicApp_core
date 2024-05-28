package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.controller.api.CrudController;
import ru.musicapp.coreservice.model.dto.music.*;
import ru.musicapp.coreservice.service.CrudEntityService;
import ru.musicapp.coreservice.service.MusicianService;
import ru.musicapp.coreservice.service.MusicianTopSongService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/musicians")
@RequiredArgsConstructor
public class MusicianController extends CrudController<MusicianCreateDto, MusicianPatchDto, MusicianDto, UUID> {

    private final MusicianTopSongService musicianTopSongService;
    private final MusicianService musicianService;


    @GetMapping("{id}/top")
    public List<SongShortDto> getTop5Songs(@PathVariable UUID id) {
        return musicianTopSongService.get(id);
    }

    @GetMapping("{id}/similar")
    public List<MusicianDto> getSimilar(@PathVariable UUID id) {
        return musicianService.findSimilarMusicians(id);
    }

    @GetMapping("short")
    public List<MusicianShortDto> getShort() {
        return musicianService.getShort();
    }

    @Override
    protected CrudEntityService<MusicianDto, MusicianCreateDto, MusicianPatchDto, UUID> getEntityService() {
        return musicianService;
    }


}
