package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.HistoryMapper;
import ru.musicapp.coreservice.model.UserDetails;
import ru.musicapp.coreservice.model.dto.playlist.HistoryDto;
import ru.musicapp.coreservice.model.entity.playlist.History;
import ru.musicapp.coreservice.repository.HistoryRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.HistoryService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repository;
    private final HistoryMapper mapper;

    @Transactional
    @Override
    public void save(UUID songId) {
        UserDetails details = SecurityContextFacade.get();
        repository.save(History.builder()
                .id(History.HistoryId.builder()
                        .userId(details.getId())
                        .songId(songId)
                        .build())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<HistoryDto> get(Integer offset, Integer limit) {
        UserDetails details = SecurityContextFacade.get();
        Page<History> page = repository.findAllByUserId(details.getId(), PageRequest.of(offset / limit, limit));
        return new PageImpl<>(
                mapper.toDtos(page.getContent()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    @Transactional
    @Override
    public void deleteBySongId(UUID songId) {
        UserDetails details = SecurityContextFacade.get();
        repository.deleteById(History.HistoryId.builder()
                .songId(songId)
                .userId(details.getId())
                .build());
    }

    @Transactional
    @Override
    public void clearAll() {
        UserDetails details = SecurityContextFacade.get();
        repository.deleteAllByUserId(details.getId());
    }
}
