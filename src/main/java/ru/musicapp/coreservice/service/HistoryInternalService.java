package ru.musicapp.coreservice.service;

import java.util.UUID;

public interface HistoryInternalService {

    void saveSongToHistory(UUID songId, UUID userId);

}
