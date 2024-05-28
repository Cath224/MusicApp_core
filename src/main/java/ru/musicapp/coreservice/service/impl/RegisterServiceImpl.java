package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.UserMapper;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.entity.user.Credentials;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.repository.CredentialsRepository;
import ru.musicapp.coreservice.repository.RoleRepository;
import ru.musicapp.coreservice.repository.UserRepository;
import ru.musicapp.coreservice.service.RegisterService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(UserCreateDto dto) {
        String email = dto.getEmail();
        String login = dto.getLogin();

        if (userRepository.existsByCredentials_Login(login)) {
            throw new RuntimeException();
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException();
        }


        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(passwordEncoder.encode(dto.getPassword()));

        credentialsRepository.save(credentials);

        User user = userMapper.fromCreateDto(dto);

        user.setCredentialsIds(credentials.getId());
        user.setRoles(Set.of(roleRepository.findByCode("DEFAULT_USER")));
        userRepository.save(user);
    }


}
