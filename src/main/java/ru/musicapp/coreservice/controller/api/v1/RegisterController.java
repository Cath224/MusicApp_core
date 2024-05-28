package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.service.RegisterService;

@RestController
@RequestMapping("api/v1/public/register")
@RequiredArgsConstructor
public class RegisterController {


    private final RegisterService service;


    @PostMapping
    public void register(@RequestBody UserCreateDto dto) {
        service.register(dto);
    }

}
