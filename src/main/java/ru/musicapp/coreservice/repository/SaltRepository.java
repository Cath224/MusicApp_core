package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.Salt;

import java.util.UUID;

@Repository
public interface SaltRepository extends EntityRepository<Salt, UUID> {
}
