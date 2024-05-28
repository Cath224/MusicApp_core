package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.adapter.PredictionServiceAdapter;
import ru.musicapp.coreservice.mapper.SongShortMapper;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistQueueElement;
import ru.musicapp.coreservice.model.entity.music.Song;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.repository.SongRepository;
import ru.musicapp.coreservice.repository.UserRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.FileService;
import ru.musicapp.coreservice.service.HistoryInternalService;
import ru.musicapp.coreservice.service.PlayService;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlayService {

    private final FileService fileService;
    private final HistoryInternalService historyInternalService;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final SongShortMapper songShortMapper;
    private final PredictionServiceAdapter predictionServiceAdapter;

    @Autowired
    @Qualifier("playlistQueueRedisTemplate")
    private RedisTemplate<String, PlaylistQueueElement> redisTemplate;

    @Transactional
    @Override
    public FileStreamDto playSong(UUID songId, String login) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException());
        User user = userRepository.findByCredentials_Login(login).orElseThrow(() -> new RuntimeException());
        historyInternalService.saveSongToHistory(songId, user.getId());
        PlaylistQueueElement playlistQueueElement = redisTemplate.opsForValue().get("playlist_" + user.getId().toString());
        if (playlistQueueElement != null) {
            LinkedHashSet<UUID> ids = playlistQueueElement.getSongIds();
            if (CollectionUtils.isNotEmpty(ids)) {
                playlistQueueElement.getSongIds().removeFirst();
                redisTemplate.opsForValue().set("playlist_" + user.getId().toString(), playlistQueueElement);
                redisTemplate.expire("playlist_" + user.getId().toString(), Duration.ofHours(12));
            }

        }

        return fileService.downloadFile(song.getFileId(), BucketName.SONG);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SongShortDto> getCurrentQueuePlaylist() {
        UserExtendedDetails details = SecurityContextFacade.get();
        PlaylistQueueElement playlistQueueElement = redisTemplate.opsForValue().get("playlist_" + details.getId().toString());
        if (playlistQueueElement == null) {
            return Collections.emptyList();
        }
        List<Song> songs = songRepository.findAllById(playlistQueueElement.getSongIds());
        Map<UUID, Song> songsByIds = songs.stream()
                .collect(Collectors.toMap(Song::getId, el -> el));

        return songShortMapper.toDtos(playlistQueueElement.getSongIds().stream().map(songsByIds::get).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void generatePlaylistQueue(UUID songId, String login) {
        User user = userRepository.findByCredentials_Login(login).orElseThrow(() -> new RuntimeException());
        PlaylistQueueElement playlistQueueElement = redisTemplate.opsForValue().get("playlist_" + user.getId().toString());
        if (playlistQueueElement != null && CollectionUtils.isNotEmpty(playlistQueueElement.getSongIds()) && playlistQueueElement.getSongIds().size() <= 2) {
            LinkedHashSet<UUID> ids = playlistQueueElement.getSongIds();
            LinkedHashSet<UUID> songsIds = songRepository.findAllIdsBySongIdOrderBySequenceNumber(songId);
            LinkedHashSet<UUID> musicianSongIds = songRepository.findAllMusicianIdsBySongIdOrderBySequenceNumber(songId);

            songsIds.addAll(musicianSongIds);

            Set<UUID> similarUserIds = predictionServiceAdapter.generateSimilarUsers(SecurityContextFacade.get().getId());
            if (!similarUserIds.isEmpty()) {
                LinkedHashSet<UUID> similarUsersSongsIds = songRepository.findAllFromHistoryLikesByUserIds(similarUserIds);
                songsIds.addAll(similarUsersSongsIds);
            }

            ids.addAll(songsIds);
        } else if (playlistQueueElement == null || CollectionUtils.isEmpty(playlistQueueElement.getSongIds())) {
            LinkedHashSet<UUID> songsIds = songRepository.findAllIdsBySongIdOrderBySequenceNumber(songId);
            LinkedHashSet<UUID> musicianSongIds = songRepository.findAllMusicianIdsBySongIdOrderBySequenceNumber(songId);

            songsIds.addFirst(songId);
            songsIds.addAll(musicianSongIds);

            Set<UUID> similarUserIds = predictionServiceAdapter.generateSimilarUsers(SecurityContextFacade.get().getId());
            if (!similarUserIds.isEmpty()) {
                LinkedHashSet<UUID> similarUsersSongsIds = songRepository.findAllFromHistoryLikesByUserIds(similarUserIds);
                songsIds.addAll(similarUsersSongsIds);
            }
            playlistQueueElement = PlaylistQueueElement.builder()
                    .songIds(songsIds)
                    .build();
        } else {
            return;
        }


        UserExtendedDetails details = SecurityContextFacade.get();
        redisTemplate.opsForValue().set("playlist_" + details.getId().toString(), playlistQueueElement);
        redisTemplate.expire("playlist_" + details.getId().toString(), Duration.ofHours(12));
    }

}
