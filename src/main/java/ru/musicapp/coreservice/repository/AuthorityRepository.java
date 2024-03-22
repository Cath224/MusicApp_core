package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.user.Authority;

@Repository
public interface AuthorityRepository extends EntityRepository<Authority, String> {

}
