package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.AlbumShortDto;
import ru.musicapp.coreservice.model.entity.music.Album;

@Mapper(config = EntityMapperConfiguration.class)
public interface AlbumShortMapper extends EntityMapper<Album, AlbumShortDto> {
}
