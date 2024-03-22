package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.UserMapper;
import ru.musicapp.coreservice.model.dto.user.UserCreateDto;
import ru.musicapp.coreservice.model.entity.user.Credentials;
import ru.musicapp.coreservice.model.entity.user.Salt;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.repository.CredentialsRepository;
import ru.musicapp.coreservice.repository.RoleRepository;
import ru.musicapp.coreservice.repository.SaltRepository;
import ru.musicapp.coreservice.repository.UserRepository;
import ru.musicapp.coreservice.service.RegisterService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final SaltRepository saltRepository;
    private final CredentialsRepository credentialsRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Value("${secret}")
    private String secret;

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

        Salt salt = new Salt();
        byte[] saltValue = new SecureRandom().generateSeed(16);
        salt.setValue(Base64.getEncoder().encodeToString(saltValue));

        saltRepository.save(salt);
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashSalt = new byte[32];
            System.arraycopy(saltValue, 0, hashSalt, 0, 16);
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(secretBytes, 16, hashSalt, 16, 16);
            digest.update(hashSalt);
            hash = digest.digest(dto.getPassword().getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setSaltId(salt.getId());
        credentials.setPassword(Base64.getEncoder().encodeToString(hash));

        credentialsRepository.save(credentials);

        User user = userMapper.fromCreateDto(dto);

        user.setCredentialsIds(credentials.getId());
        user.setRoles(Set.of(roleRepository.findByCode("DEFAULT_USER")));
        userRepository.save(user);
    }


}
