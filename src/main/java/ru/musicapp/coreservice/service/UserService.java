package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.dto.user.UserDto;
import ru.musicapp.coreservice.model.dto.user.UserPatchDto;

import java.util.UUID;

public interface UserService extends CrudEntityService<UserDto, UserCreateDto, UserPatchDto, UUID> {

    UserDto getCurrent();

}
