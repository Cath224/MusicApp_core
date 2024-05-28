package ru.musicapp.coreservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.playlist.History;

import java.util.UUID;

@Repository
public interface HistoryRepository extends EntityRepository<History, History.HistoryId> {

    @Modifying
    void deleteAllByUserId(@Param("userId") UUID userId);

    Page<History> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

}
