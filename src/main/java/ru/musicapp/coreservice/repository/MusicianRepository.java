package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Musician;

import java.util.List;
import java.util.UUID;

@Repository
public interface MusicianRepository extends EntityRepository<Musician, UUID> {

    @Query(value = """
            select m.* from core_service.musician m
            inner join core_service.album a on m.id = a.musician_id
            inner join core_service.album_genre ag on a.id = ag.album_id
            where m.id != :id and ag.genre in (select g2.title from core_service.genre g2 inner join core_service.album_genre ag2 on g2.title = ag2.genre inner join core_service.album a2 on ag2.album_id = a2.id where a2.musician_id = :id)
            offset 0
            limit 20
            """, nativeQuery = true)
    List<Musician> findAllSimilarById(@Param("id") UUID id);

}
