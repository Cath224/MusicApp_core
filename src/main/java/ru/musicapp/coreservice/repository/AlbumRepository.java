package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Album;

import java.util.UUID;

@Repository
public interface AlbumRepository extends EntityRepository<Album, UUID> {
}
