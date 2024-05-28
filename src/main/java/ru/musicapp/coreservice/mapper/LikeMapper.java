package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.musicapp.coreservice.model.dto.playlist.LikeCreateDto;
import ru.musicapp.coreservice.model.dto.playlist.LikeDto;
import ru.musicapp.coreservice.model.entity.playlist.Like;

@Mapper(config = EntityMapperConfiguration.class, uses = {SongShortMapper.class})
public interface LikeMapper extends EntityMapper<Like, LikeDto> {

    @Mappings({
            @Mapping(source = "userId", target = "id.userId"),
            @Mapping(source = "songId", target = "id.songId")
    })
    @Override
    Like toEntity(LikeDto likeDto);

    @Mappings({
            @Mapping(source = "id.userId", target = "userId"),
            @Mapping(source = "id.songId", target = "songId")
    })
    @Override
    LikeDto toDto(Like entity);

    @Mappings({
            @Mapping(source = "songId", target = "id.songId")
    })
    Like fromCreateDto(LikeCreateDto dto);
}
