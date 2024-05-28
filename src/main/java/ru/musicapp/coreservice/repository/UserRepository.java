package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends EntityRepository<User, UUID> {

    Optional<User> findByCredentials_Login(@Param("login") String login);

    boolean existsByCredentials_Login(@Param("login") String login);

    boolean existsByEmail(@Param("email") String email);

    @Query(value = """
            select case when count(u.id) = 1 then true else false end
                         from core_service."user" u
                                  inner join core_service.user_role ur on u.id = ur.user_id
                                  inner join core_service.role_authority ra on ur.role_id = ra.role_id
                         where u.id = :id
                         and ra.authority_code in :authorities
                         group by u.id
            """, nativeQuery = true)
    Boolean existsByIdAndAuthorities(@Param("id") UUID id, @Param("authorities") Set<String> authorities);
}
