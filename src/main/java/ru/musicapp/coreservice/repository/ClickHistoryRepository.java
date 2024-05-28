package ru.musicapp.coreservice.repository;

import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.stat.ClickHistory;

import java.util.UUID;

@Repository
public interface ClickHistoryRepository extends EntityRepository<ClickHistory, UUID> {
}
