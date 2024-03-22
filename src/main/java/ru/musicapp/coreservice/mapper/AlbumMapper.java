package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;
import ru.musicapp.coreservice.model.entity.music.Album;

@Mapper(config = EntityMapperConfiguration.class)
public interface AlbumMapper extends CrudEntityMapper<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto> {
}
