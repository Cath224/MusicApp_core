package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.PlaylistSong;

@Repository
public interface PlaylistSongRepository extends EntityRepository<PlaylistSong, PlaylistSong.PlaylistSongId> {
}
