package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.LikeMapper;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;
import ru.musicapp.coreservice.model.entity.playlist.Like;
import ru.musicapp.coreservice.repository.LikeRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.LikeService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository repository;
    private final LikeMapper mapper;

    @Transactional
    @Override
    public void create(UUID songId) {
        UserExtendedDetails details = SecurityContextFacade.get();
        Like like = Like.builder()
                .id(Like.LikeId.builder()
                        .songId(songId)
                        .userId(details.getId())
                        .build())
                .build();
        repository.save(like);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LikeDto> get(Integer offset, Integer limit) {
        UserExtendedDetails details = SecurityContextFacade.get();
        Page<Like> page = repository.findAllByUserId(details.getId(), PageRequest.of(offset / limit, limit));
        return new PageImpl<>(
                mapper.toDtos(page.getContent()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public LikeDto getBySongId(UUID songId) {
        UserExtendedDetails details = SecurityContextFacade.get();
        return mapper.toDto(repository.findById(Like.LikeId.builder()
                        .userId(details.getId())
                        .songId(songId)
                        .build())
                .orElse(null));
    }

    @Transactional
    @Override
    public void delete(UUID songId) {
        UserExtendedDetails details = SecurityContextFacade.get();
        repository.deleteById(Like.LikeId.builder()
                .songId(songId)
                .userId(details.getId())
                .build());
    }
}
