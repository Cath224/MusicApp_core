package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.LikeMapper;
import ru.musicapp.coreservice.model.UserDetails;
import ru.musicapp.coreservice.model.dto.playlist.LikeCreateDto;
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
    public void create(LikeCreateDto dto) {
        Like like = mapper.fromCreateDto(dto);
        UserDetails details = SecurityContextFacade.get();
        like.getId().setUserId(details.getId());
        repository.save(like);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LikeDto> get(Integer offset, Integer limit) {
        UserDetails details = SecurityContextFacade.get();
        Page<Like> page = repository.findAllByUserId(details.getId(), PageRequest.of(offset / limit, limit));
        return new PageImpl<>(
                mapper.toDtos(page.getContent()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    @Transactional
    @Override
    public void delete(UUID songId) {
        UserDetails details = SecurityContextFacade.get();
        repository.deleteById(Like.LikeId.builder()
                .songId(songId)
                .userId(details.getId())
                .build());
    }
}
