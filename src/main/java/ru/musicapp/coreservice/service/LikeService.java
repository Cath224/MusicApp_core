package ru.musicapp.coreservice.service;

import org.springframework.data.domain.Page;
import ru.musicapp.coreservice.model.dto.playlist.LikeCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;

import java.util.UUID;

public interface LikeService {

    void create(LikeCreateDto dto);

    Page<LikeDto> get(Integer offset, Integer limit);

    void delete(UUID songId);

}