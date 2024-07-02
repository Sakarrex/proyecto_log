package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.RequirementEntity;
import org.example.proyecto_log.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StopRepository extends PagingAndSortingRepository<StopEntity, Integer>, CrudRepository<StopEntity, Integer>, JpaRepository<StopEntity,Integer> {
}
