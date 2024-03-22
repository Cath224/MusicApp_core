package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.controller.api.CrudController;
import ru.musicapp.coreservice.model.dto.music.*;
import ru.musicapp.coreservice.service.AlbumService;
import ru.musicapp.coreservice.service.CrudEntityService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/albums")
@RequiredArgsConstructor
public class AlbumController  extends CrudController<AlbumCreateDto, AlbumPatchDto, AlbumDto, UUID> {

    private final AlbumService service;

    @Override
    protected CrudEntityService<AlbumDto, AlbumCreateDto, AlbumPatchDto, UUID> getEntityService() {
        return service;
    }
}
