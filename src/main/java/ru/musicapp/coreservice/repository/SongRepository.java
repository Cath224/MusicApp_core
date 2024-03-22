package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Song;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SongRepository extends EntityRepository<Song, UUID> {

    @Query("""
            select s.id from Song s
             inner join History h on s.id = h.song.id
             inner join Album a on s.albumId = a.id
             where a.musicianId = :musicianId
             group by s.id
             order by count(h.user.id) desc
             limit 5
            """)
    Set<UUID> findTopIds5ByMusicianId(@Param("musicianId") UUID musicianId);

}
