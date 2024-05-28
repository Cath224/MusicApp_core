package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;
import ru.musicapp.coreservice.model.entity.music.Song;

@Mapper(config = EntityMapperConfiguration.class)
public interface SongShortMapper extends EntityMapper<Song, SongShortDto> {
}
