package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.file.File;

import java.util.UUID;

@Repository
public interface FileRepository extends EntityRepository<File, UUID> {
}
