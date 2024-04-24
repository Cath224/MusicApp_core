package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import ru.musicapp.coreservice.model.dto.user.RoleDto;
import ru.musicapp.coreservice.model.entity.user.Role;

@Mapper(config = EntityMapperConfiguration.class)
public interface RoleMapper extends EntityMapper<Role, RoleDto> {
}
