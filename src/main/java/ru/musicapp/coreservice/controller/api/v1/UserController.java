package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.musicapp.coreservice.controller.api.CrudController;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.dto.user.UserDto;
import ru.musicapp.coreservice.model.dto.user.UserPatchDto;
import ru.musicapp.coreservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/users")
@RequiredArgsConstructor
public class UserController extends CrudController<UserCreateDto, UserPatchDto, UserDto, UUID> {

    private final UserService service;

    @Override
    protected UserService getEntityService() {
        return service;
    }

    @GetMapping("current")
    public UserDto getCurrent() {
        return service.getCurrent();
    }
}
