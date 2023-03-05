package net.atos.rest.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.atos.rest.proyecto.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
