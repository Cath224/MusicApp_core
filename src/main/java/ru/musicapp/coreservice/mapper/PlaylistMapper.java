package ru.musicapp.coreservice.mapper;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistDto;
import ru.musicapp.coreservice.model.dto.playlist.PlaylistPatchDto;
import ru.musicapp.coreservice.model.entity.music.PlaylistSong;
import ru.musicapp.coreservice.model.entity.playlist.Playlist;

import java.util.Collections;
import java.util.List;

@Mapper(config = EntityMapperConfiguration.class)
public interface PlaylistMapper extends CrudEntityMapper<Playlist, PlaylistDto, PlaylistCreateDto, PlaylistPatchDto> {

    @Override
    @Mappings({@Mapping(source = "playlistSongs", target = "songs")})
    PlaylistDto toDto(Playlist entity);

    default List<SongShortDto> playlistSongsToShortSongsDtos(List<PlaylistSong> playlistSongs) {
        if (CollectionUtils.isEmpty(playlistSongs)) {
            return Collections.emptyList();
        }
        SongShortMapper songShortMapper = Mappers.getMapper(SongShortMapper.class);
        return playlistSongs.stream()
                .map(PlaylistSong::getSong)
                .map(songShortMapper::toDto)
                .toList();
    }
}
