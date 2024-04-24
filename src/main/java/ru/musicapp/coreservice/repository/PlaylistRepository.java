package ru.musicapp.coreservice.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.playlist.Playlist;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaylistRepository extends EntityRepository<Playlist, UUID> {

    Optional<Playlist> findByIdAndAndCreatedBy(@Param("id") UUID id, @Param("createdBy") UUID createdBy);

}
