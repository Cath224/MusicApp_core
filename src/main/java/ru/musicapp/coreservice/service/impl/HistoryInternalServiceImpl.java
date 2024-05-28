package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.model.entity.playlist.History;
import ru.musicapp.coreservice.repository.HistoryRepository;
import ru.musicapp.coreservice.service.HistoryInternalService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryInternalServiceImpl implements HistoryInternalService {

    private final HistoryRepository historyRepository;

    @Transactional
    @Async
    @Override
    public void saveSongToHistory(UUID songId, UUID userId) {
        historyRepository.save(History.builder()
                .id(History.HistoryId.builder()
                        .songId(songId)
                        .userId(userId)
                        .build())
                .build());
    }
}
