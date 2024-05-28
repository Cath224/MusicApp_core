package ru.musicapp.coreservice.service.impl;

import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.CrudEntityMapper;
import ru.musicapp.coreservice.mapper.PlaylistMapper;
import ru.musicapp.coreservice.model.QueryParams;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistPatchDto;
import ru.musicapp.coreservice.model.entity.music.PlaylistSong;
import ru.musicapp.coreservice.model.entity.music.Song;
import ru.musicapp.coreservice.model.entity.playlist.Playlist;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.PlaylistRepository;
import ru.musicapp.coreservice.repository.PlaylistSongRepository;
import ru.musicapp.coreservice.repository.SongRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.PlaylistService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl extends CrudEntityServiceImpl<Playlist, PlaylistDto, PlaylistCreateDto, PlaylistPatchDto, UUID> implements PlaylistService {


    private final PlaylistRepository repository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public PlaylistDto create(PlaylistCreateDto playlistCreateDto) {
        PlaylistDto playlistDto = super.create(playlistCreateDto);
        Set<UUID> songIds = playlistCreateDto.getSongIds();
        if (CollectionUtils.isNotEmpty(songIds)) {
            List<Song> songs = songRepository.findAllById(songIds);
            if (songs.isEmpty() || songs.size() != songIds.size()) {
                throw new RuntimeException();
            }
            List<PlaylistSong> playlistSongs = songIds.stream()
                    .map((el) -> PlaylistSong.builder()
                            .id(PlaylistSong.PlaylistSongId.builder()
                                    .playlistId(playlistDto.getId())
                                    .songId(el)
                                    .build())
                            .build())
                    .toList();
            playlistSongRepository.saveAll(playlistSongs);
        }
        return playlistDto;
    }

    @Transactional
    @Override
    public PlaylistDto update(PlaylistPatchDto playlistPatchDto, UUID id) {
        PlaylistDto playlistDto = super.update(playlistPatchDto, id);
        Set<UUID> songIds = playlistPatchDto.getSongIds();
        if (CollectionUtils.isNotEmpty(songIds)) {
            List<Song> songs = songRepository.findAllById(songIds);
            if (songs.isEmpty() || songs.size() != songIds.size()) {
                throw new RuntimeException();
            }
            List<PlaylistSong> playlistSongs = songIds.stream()
                    .map((el) -> PlaylistSong.builder()
                            .id(PlaylistSong.PlaylistSongId.builder()
                                    .playlistId(playlistDto.getId())
                                    .songId(el)
                                    .build())
                            .build())
                    .toList();
            playlistSongRepository.saveAll(playlistSongs);
        }
        return playlistDto;
    }

    @Transactional
    @Cacheable(cacheNames = "playlist", key = "#uuid")
    @Override
    public PlaylistDto getById(UUID uuid) {
        return super.getById(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    protected Playlist getByIdInternal(UUID id) {
        UserExtendedDetails details = SecurityContextFacade.get();
        return repository.findByIdAndAndCreatedBy(id, details.getId())
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PlaylistDto> get(QueryParams params) {
        UserExtendedDetails details = SecurityContextFacade.get();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Playlist> query = criteriaBuilder.createQuery(Playlist.class);
        Root<Playlist> root = query.from(Playlist.class);

        Predicate predicate = criteriaBuilder.equal(root.get("createdBy"), details.getId());

        String queryRsql = params.getQuery();
        if (StringUtils.isNotEmpty(queryRsql)) {
            Predicate predicateFromRsqlQuery = RSQLJPASupport.<Playlist>rsql(queryRsql).toPredicate(root, query, criteriaBuilder);
            predicate = criteriaBuilder.and(predicate, predicateFromRsqlQuery);
        }

        query.where(predicate);


        List<Playlist> result = entityManager.createQuery(query)
                .setFirstResult(params.getOffset())
                .setMaxResults(params.getLimit())
                .getResultList();

        Predicate countPredicate = predicate;
        return PageableExecutionUtils.getPage(mapper.toDtos(result), params.getPageRequest(),
                () -> {
                    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
                    countQuery.where(countPredicate);
                    countQuery.select(criteriaBuilder.count(root.get("id")));
                    return entityManager.createQuery(countQuery).getSingleResult();
                });
    }

    @Transactional
    @Override
    public void deleteSongs(UUID id, Set<UUID> songsIdsToDelete) {
        playlistSongRepository.deleteAllByPlaylistIdAndSong_IdIn(id, songsIdsToDelete);
    }

    @Override
    protected EntityRepository<Playlist, UUID> getRepository() {
        return repository;
    }

    @Override
    protected CrudEntityMapper<Playlist, PlaylistDto, PlaylistCreateDto, PlaylistPatchDto> getMapper() {
        return mapper;
    }


}
