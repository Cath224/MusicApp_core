package ru.musicapp.coreservice.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.Role;

import java.util.UUID;

@Repository
public interface RoleRepository extends EntityRepository<Role, UUID> {

    Role findByCode(@Param("code") String code);

}
