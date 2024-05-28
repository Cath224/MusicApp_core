package ru.musicapp.coreservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.dto.user.UserDto;
import ru.musicapp.coreservice.model.dto.user.UserPatchDto;
import ru.musicapp.coreservice.model.entity.user.User;

@Mapper(config = EntityMapperConfiguration.class)
public interface UserMapper extends CrudEntityMapper<User, UserDto, UserCreateDto, UserPatchDto> {

    @Override
    @Mappings({
            @Mapping(source = "credentials.login", target = "login")
    })
    UserDto toDto(User entity);

    @Override
    User toEntity(UserDto userDto);
}
