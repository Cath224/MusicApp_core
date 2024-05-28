package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.service.TopsService;

import java.util.List;

@RestController
@RequestMapping("api/v1/public/tops")
@RequiredArgsConstructor
public class TopsController {

    private final TopsService service;


    @GetMapping("songs")
    public List<SongDto> getTopSongs() {
        return service.getTop10Songs();
    }

    @GetMapping("songs-newest")
    public List<SongDto> getTopNewestSongs() {
        return service.getNewestTop10Songs();
    }

    @GetMapping("musician")
    public List<MusicianDto> getTop10Musicians() {
        return service.getTop10Musicians();
    }

}
