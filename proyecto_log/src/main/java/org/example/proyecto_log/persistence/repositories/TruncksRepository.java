package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.RequirementEntity;
import org.example.proyecto_log.persistence.entity.TrunckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TruncksRepository extends PagingAndSortingRepository<TrunckEntity, Integer>, CrudRepository<TrunckEntity, Integer>, JpaRepository<TrunckEntity,Integer> {
}
