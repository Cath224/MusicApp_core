package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.model.PageResponse;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;
import ru.musicapp.coreservice.service.LikeService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService service;


    @PostMapping("{songId}")
    public void create(@PathVariable UUID songId) {
        service.create(songId);
    }

    @DeleteMapping("{songId}")
    public void delete(@PathVariable UUID songId) {
        service.delete(songId);
    }

    @GetMapping
    public PageResponse<LikeDto> get(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                     @RequestParam(required = false, defaultValue = "1000") Integer limit) {
        return PageResponse.of(service.get(offset, limit));
    }

    @GetMapping("by-song-id/{songId}")
    public LikeDto getBySongId(@PathVariable UUID songId) {
        return service.getBySongId(songId);
    }
}
