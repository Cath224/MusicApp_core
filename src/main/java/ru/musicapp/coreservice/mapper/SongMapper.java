package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.SongCreateDto;
import ru.musicapp.coreservice.model.dto.music.SongDto;
import ru.musicapp.coreservice.model.dto.music.SongPatchDto;
import ru.musicapp.coreservice.model.entity.music.Song;

@Mapper(config = EntityMapperConfiguration.class)
public interface SongMapper extends CrudEntityMapper<Song, SongDto, SongCreateDto, SongPatchDto> {
}
