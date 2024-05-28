package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.music.Genre;

@Repository
public interface GenreRepository extends EntityRepository<Genre, String>{
}
