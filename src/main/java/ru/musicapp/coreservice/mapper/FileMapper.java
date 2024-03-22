package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.entity.file.File;

@Mapper(config = EntityMapperConfiguration.class)
public interface FileMapper extends EntityMapper<File, FileDto> {
}
