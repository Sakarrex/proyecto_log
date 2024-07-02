package org.example.proyecto_log.persistence.repositories;

import org.example.proyecto_log.persistence.entity.RequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequirementRepository extends PagingAndSortingRepository<RequirementEntity, Integer>, CrudRepository<RequirementEntity, Integer>, JpaRepository<RequirementEntity,Integer> {
long countByCategory(String category);
}
