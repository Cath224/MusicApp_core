package ru.musicapp.coreservice.service;

import org.springframework.data.domain.Page;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;

import java.util.UUID;

public interface LikeService {

    void create(UUID songId);

    Page<LikeDto> get(Integer offset, Integer limit);

    LikeDto getBySongId(UUID songId);

    void delete(UUID songId);

}