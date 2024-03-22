package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Musician;

import java.util.UUID;

@Repository
public interface MusicianRepository extends EntityRepository<Musician, UUID> {
}
