package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.controller.api.CrudController;
import ru.musicapp.coreservice.model.dto.music.SongCreateDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.model.dto.music.SongPatchDto;
import ru.musicapp.coreservice.service.CrudEntityService;
import ru.musicapp.coreservice.service.SongService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/songs")
@RequiredArgsConstructor
public class SongController extends CrudController<SongCreateDto, SongPatchDto, SongDto, UUID> {

    private final SongService service;

    @Override
    protected CrudEntityService<SongDto, SongCreateDto, SongPatchDto, UUID> getEntityService() {
        return service;
    }


}
