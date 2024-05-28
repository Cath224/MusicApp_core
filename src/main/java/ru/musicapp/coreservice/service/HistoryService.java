package ru.musicapp.coreservice.service;

import org.springframework.data.domain.Page;
import ru.musicapp.coreservice.model.dto.playlist.HistoryDto;

import java.util.UUID;

public interface HistoryService {

    void save(UUID songId);

    Page<HistoryDto> get(Integer offset, Integer limit);

    void deleteBySongId(UUID songId);

    void clearAll();
}
