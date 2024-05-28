package ru.musicapp.coreservice.service;

import ru.musicapp.coreservice.model.dto.security.AuthDto;
import ru.musicapp.coreservice.model.dto.security.JwtTokenResponse;

public interface AuthService {

    JwtTokenResponse auth(AuthDto dto);

}
