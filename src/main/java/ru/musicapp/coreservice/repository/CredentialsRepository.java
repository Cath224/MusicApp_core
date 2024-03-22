package ru.musicapp.coreservice.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.Credentials;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialsRepository extends EntityRepository<Credentials, UUID> {

    Optional<Credentials> findByLogin(@Param("login") String login);

}
