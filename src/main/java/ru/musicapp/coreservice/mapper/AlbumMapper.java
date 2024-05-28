package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.music.AlbumCreateDto;
import ru.musicapp.coreservice.model.dto.music.AlbumDto;
import ru.musicapp.coreservice.model.dto.music.AlbumPatchDto;
import ru.musicapp.coreservice.model.entity.music.Album;
import ru.musicapp.coreservice.model.entity.music.Genre;

@Mapper(config = EntityMapperConfiguration.class, uses = {MusicianShortMapper.class})
public interface AlbumMapper extends CrudEntityMapper<Album, AlbumDto, AlbumCreateDto, AlbumPatchDto> {

    static String fromGenreToString(Genre genre) {
        if (genre == null) {
            return null;
        }
        return genre.getTitle();
    }

    static Genre fromStringToGenre(String title) {
        if (title == null) {
            return null;
        }
        return Genre.builder()
                .title(title)
                .build();
    }

}
