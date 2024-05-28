package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.UserMapper;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.dto.user.UserDto;
import ru.musicapp.coreservice.model.dto.user.UserPatchDto;
import ru.musicapp.coreservice.model.entity.user.Credentials;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.repository.CredentialsRepository;
import ru.musicapp.coreservice.repository.UserRepository;
import ru.musicapp.coreservice.security.SecurityContextFacade;
import ru.musicapp.coreservice.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudEntityServiceImpl<User, UserDto, UserCreateDto, UserPatchDto, UUID> implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsRepository credentialsRepository;

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    protected UserMapper getMapper() {
        return mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getCurrent() {
        return mapper.toDto(repository.findById(SecurityContextFacade.get().getId()).orElseThrow(RuntimeException::new));
    }

    @Transactional
    @Override
    public UserDto update(UserPatchDto userPatchDto, UUID id) {
        User entity = getByIdInternal(id);

        String password = userPatchDto.getPassword();

        if (StringUtils.isNotBlank(password)) {
            password = passwordEncoder.encode(password);
            Credentials credentials = entity.getCredentials();
            if (!credentials.getPassword().equals(password)) {
                credentialsRepository.updatePasswordById(password, credentials.getId());
            }
        }

        entity = getMapper().partialUpdate(entity, userPatchDto);

        entity = getRepository().save(entity);

        return getMapper().toDto(entity);
    }
}
