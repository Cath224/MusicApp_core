package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.MusicianCreateDto;
import ru.musicapp.coreservice.model.dto.music.MusicianDto;
import ru.musicapp.coreservice.model.dto.music.MusicianPatchDto;
import ru.musicapp.coreservice.model.entity.music.Musician;

@Mapper(config = EntityMapperConfiguration.class)
public interface MusicianMapper extends CrudEntityMapper<Musician, MusicianDto, MusicianCreateDto, MusicianPatchDto> {
}
