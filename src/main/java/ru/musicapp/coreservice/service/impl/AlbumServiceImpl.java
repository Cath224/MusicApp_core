package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.AlbumMapper;
import ru.musicapp.coreservice.mapper.AlbumShortMapper;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;
import ru.musicapp.coreservice.model.dto.music.AlbumShortDto;
import ru.musicapp.coreservice.model.entity.music.Album;
import ru.musicapp.coreservice.model.entity.music.Genre;
import ru.musicapp.coreservice.model.type.ElementType;
import ru.musicapp.coreservice.repository.AlbumRepository;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.GenreRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.AlbumService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl extends CrudHistoryEntityServiceImpl<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto, UUID> implements AlbumService {

    private final AlbumRepository repository;
    private final AlbumMapper mapper;
    private final AlbumShortMapper shortMapper;
    private final GenreRepository genreRepository;

    @Override
    protected EntityRepository<Album, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto> getMapper() {
        return mapper;
    }


    @Transactional
    @Override
    public AlbumDto create(AlbumCreateDto albumCreateDto) {
        Album entity = getMapper().fromCreateDto(albumCreateDto);
        entity = getRepository().save(entity);
        Set<String> genres = albumCreateDto.getGenres();
        if (CollectionUtils.isNotEmpty(genres)) {
            genres = genres.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
            List<Genre> existedGenres = genreRepository.findAllById(genres);
            Map<String, Genre> existedGenresTitles = existedGenres.stream().collect(Collectors.toMap(Genre::getTitle, Function.identity()));
            for (String genre : genres) {
                Genre existedGenre = existedGenresTitles.get(genre);
                if (existedGenre == null) {
                    existedGenre = Genre.builder()
                            .title(genre)
                            .build();
                    existedGenres.add(existedGenre);
                }
            }
            genreRepository.saveAll(existedGenres);
            if (entity.getGenres() == null) {
                entity.setGenres(new HashSet<>(existedGenres));
            } else {
                entity.getGenres().addAll(existedGenres);
            }
            repository.save(entity);
        }

        return mapper.toDto(entity);
    }

    @Transactional
    @Cacheable(cacheNames = "album", key = "#uuid")
    @Override
    public AlbumDto getById(UUID uuid) {
        return super.getById(uuid);
    }

    @Override
    protected void saveToClickHistory(UUID id) {
        clickHistoryService.saveAlbumClick(id, SecurityContextFacade.get().getId());
    }

    @Override
    protected void saveSearchToClickHistory(Collection<AlbumDto> albumDtos) {
        if (CollectionUtils.isEmpty(albumDtos)) {
            return;
        }
        EnumMap<ElementType, Set<String>> enumMap = new EnumMap<>(Map.of(ElementType.ALBUM,
                albumDtos.stream().map((el) -> String.valueOf(el.getId())).collect(Collectors.toSet())));
        clickHistoryService.saveSearch(enumMap, SecurityContextFacade.get().getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlbumShortDto> getShort() {
        return shortMapper.toDtos(repository.findAll());
    }
}
