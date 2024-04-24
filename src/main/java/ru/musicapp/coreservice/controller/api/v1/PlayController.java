package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;
import ru.musicapp.coreservice.service.PlayService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/play")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService service;

    @GetMapping(value = "song/{songId}", produces = {"audio/wave"})
    public ResponseEntity<StreamingResponseBody> playSong(@PathVariable UUID songId, @RequestParam String login) {
        FileStreamDto songDto = service.playSong(songId, login);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/wave"))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment").filename(songDto.getFilename()).build().toString())
                .body((out) -> {
                    try (InputStream is = songDto.getInputStream().get()) {
                        is.transferTo(out);
                    }
                });
    }


    @PostMapping("generate/by-song-id/{songId}")
    public void generatePlaylistQueue(@PathVariable UUID songId) {
        service.generatePlaylistQueue(songId);
    }

    @GetMapping("current-queue")
    public List<SongShortDto> getCurrentQueue() {
        return service.getCurrentQueuePlaylist();
    }

}
