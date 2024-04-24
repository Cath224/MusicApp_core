package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Song;

import java.util.*;

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

    @Query("select s.id from Song s inner join History h on s.id = h.song.id inner join Like l on s.id = l.song.id where h.user.id in :userIds or l.user.id in :userIds")
    LinkedHashSet<UUID> findAllFromHistoryLikesByUserIds(@Param("userIds") Set<UUID> userIds);


    @Query("""
            select s.id from Song s
            inner join Album a on s.albumId = a.id
            where a.id = (select s2.albumId from Song s2 where s2.id = :songId)
                  and s.id != :songId
            order by s.sequenceNumber asc
            """)
    LinkedHashSet<UUID> findAllIdsBySongIdOrderBySequenceNumber(@Param("songId") UUID songId);

    @Query("""
            select s.id from Song s
            inner join Album a on s.albumId = a.id
            inner join Musician m on a.musicianId = m.id
            where a.id != (select s2.albumId from Song s2 where s2.id = :songId)
            order by s.sequenceNumber asc
            """)
    LinkedHashSet<UUID> findAllMusicianIdsBySongIdOrderBySequenceNumber(@Param("songId") UUID songId);


}
