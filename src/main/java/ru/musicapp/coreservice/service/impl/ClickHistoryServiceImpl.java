package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.model.entity.stat.ClickHistory;
import ru.musicapp.coreservice.model.type.ElementType;
import ru.musicapp.coreservice.repository.ClickHistoryRepository;
import ru.musicapp.coreservice.service.ClickHistoryService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClickHistoryServiceImpl implements ClickHistoryService {

    private final ClickHistoryRepository repository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Async("coreServiceExecutorService")
    @Override
    public void saveAlbumClick(UUID albumId, UUID userId) {
        saveClickHistory(String.valueOf(albumId), ElementType.ALBUM, userId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Async("coreServiceExecutorService")
    @Override
    public void saveMusicianClick(UUID musicianId, UUID userId) {
        saveClickHistory(String.valueOf(musicianId), ElementType.MUSICIAN, userId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Async("coreServiceExecutorService")
    @Override
    public void saveSearch(EnumMap<ElementType, Set<String>> elements, UUID userId) {
        List<ClickHistory> clickHistories = new LinkedList<>();
        elements.forEach((type, ids) -> {
            for (String id : ids) {
                clickHistories.add(ClickHistory.builder()
                        .elementType(type)
                        .elementId(id)
                        .search(true)
                        .userId(userId)
                        .build());
            }
        });
        repository.saveAll(clickHistories);
    }

    private void saveClickHistory(String id, ElementType type, UUID userId) {
        ClickHistory clickHistory = ClickHistory.builder()
                .elementType(type)
                .elementId(id)
                .userId(userId)
                .build();
        repository.save(clickHistory);
    }
}
