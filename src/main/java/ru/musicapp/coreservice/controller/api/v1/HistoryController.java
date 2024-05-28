package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.musicapp.coreservice.model.PageResponse;
import ru.musicapp.coreservice.model.dto.playlist.HistoryDto;
import ru.musicapp.coreservice.service.HistoryService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService service;

    @GetMapping
    public PageResponse<HistoryDto> get(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                        @RequestParam(required = false, defaultValue = "1000") Integer limit) {
        return PageResponse.of(service.get(offset, limit));
    }

    @DeleteMapping("by-song/{songId}")
    public void deleteBySongId(@PathVariable UUID songId) {
        service.deleteBySongId(songId);
    }

    @DeleteMapping("clear")
    public void clearAll() {
        service.clearAll();
    }


}
