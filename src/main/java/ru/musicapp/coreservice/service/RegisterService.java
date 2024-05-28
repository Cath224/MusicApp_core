package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.user.UserCreateDto;

public interface RegisterService {

    void register(UserCreateDto dto);

}
