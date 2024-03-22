package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.model.PageResponse;
import ru.musicapp.coreservice.model.dto.playlist.LikeCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;
import ru.musicapp.coreservice.service.LikeService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService service;


    @PostMapping
    public void create(@RequestBody LikeCreateDto dto) {
        service.create(dto);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID songId) {
        service.delete(songId);
    }

    @GetMapping
    public PageResponse<LikeDto> get(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                     @RequestParam(required = false, defaultValue = "1000") Integer limit) {
        return PageResponse.of(service.get(offset, limit));
    }
}
