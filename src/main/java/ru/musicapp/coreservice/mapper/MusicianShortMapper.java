package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.MusicianShortDto;
import ru.musicapp.coreservice.model.entity.music.Musician;

@Mapper(config = EntityMapperConfiguration.class)
public interface MusicianShortMapper extends EntityMapper<Musician, MusicianShortDto> {
}
