package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;

import java.util.List;
import java.util.UUID;

public interface PlayService {

    FileStreamDto playSong(UUID songId, String login);

    List<SongShortDto> getCurrentQueuePlaylist();

    void generatePlaylistQueue(UUID songId, String login);
}
