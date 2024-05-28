package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.PlaylistSong;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PlaylistSongRepository extends EntityRepository<PlaylistSong, PlaylistSong.PlaylistSongId> {

    @Modifying
    void deleteAllByPlaylistIdAndSong_IdIn(@Param("playlistId") UUID playlistId, @Param("ids") Set<UUID> ids);

}
