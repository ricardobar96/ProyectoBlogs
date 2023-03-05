package net.atos.rest.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.atos.rest.proyecto.model.Project;

@Repository
public interface ProjectRepository extends  JpaRepository<Project, Long> {
	
}

