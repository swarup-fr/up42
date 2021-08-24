package com.fr.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fr.core.model.FieldMapping;


@Repository
public interface FieldMappingsRepository extends JpaRepository<FieldMapping, Long> {

	
}
