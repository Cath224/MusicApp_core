package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.musicapp.coreservice.model.dto.playlist.HistoryDto;
import ru.musicapp.coreservice.model.entity.playlist.History;

@Mapper(config = EntityMapperConfiguration.class, uses = {SongShortMapper.class})
public interface HistoryMapper extends EntityMapper<History, HistoryDto> {

    @Mappings({
            @Mapping(source = "userId", target = "id.userId"),
            @Mapping(source = "songId", target = "id.songId")
    })
    @Override
    History toEntity(HistoryDto historyDto);

    @Mappings({
            @Mapping(source = "id.userId", target = "userId"),
            @Mapping(source = "id.songId", target = "songId")
    })
    @Override
    HistoryDto toDto(History entity);
}
