package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.file.File;

import java.util.Set;
import java.util.UUID;

@Repository
public interface FileRepository extends EntityRepository<File, UUID> {

    @Modifying
    void deleteAllByIdIn(@Param("ids") Set<UUID> ids);

}
