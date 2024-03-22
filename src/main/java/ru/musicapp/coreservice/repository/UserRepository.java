package ru.musicapp.coreservice.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends EntityRepository<User, UUID> {

    Optional<User> findByCredentials_Login(@Param("login") String login);

    boolean existsByCredentials_Login(@Param("login") String login);

    boolean existsByEmail(@Param("email") String email);
}
