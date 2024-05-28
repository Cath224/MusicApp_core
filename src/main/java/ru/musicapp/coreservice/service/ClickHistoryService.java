package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.type.ElementType;

import java.util.EnumMap;
import java.util.Set;
import java.util.UUID;

public interface ClickHistoryService {

    void saveAlbumClick(UUID albumId, UUID userId);

    void saveMusicianClick(UUID musicianId, UUID userId);

    void saveSearch(EnumMap<ElementType, Set<String>> elements, UUID userId);

}
