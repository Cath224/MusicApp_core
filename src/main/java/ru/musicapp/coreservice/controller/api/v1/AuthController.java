package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.model.dto.security.AuthDto;
import ru.musicapp.coreservice.model.dto.security.JwtTokenResponse;
import ru.musicapp.coreservice.service.AuthService;

@RestController
@RequestMapping("api/v1/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public JwtTokenResponse auth(@RequestBody AuthDto dto) {
        return authService.auth(dto);
    }


}

