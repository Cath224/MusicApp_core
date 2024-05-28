package ru.musicapp.coreservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musicapp.coreservice.model.entity.playlist.Like;

import java.util.UUID;

@Repository
public interface LikeRepository extends EntityRepository<Like, Like.LikeId> {

    Page<Like> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

}
