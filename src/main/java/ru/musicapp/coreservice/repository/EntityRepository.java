package ru.musicapp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<E, Id> extends JpaRepository<E, Id>, JpaSpecificationExecutor<E> {
}
