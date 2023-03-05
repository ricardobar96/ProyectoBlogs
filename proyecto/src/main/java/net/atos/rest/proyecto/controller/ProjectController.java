package net.atos.rest.proyecto.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.atos.rest.proyecto.dto.ProjectResponseDto;
import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Project;
import net.atos.rest.proyecto.service.ProjectService;

@Slf4j
@OpenAPIDefinition(info = 
@Info(title = "API REST", version = "0.1", description = "API REST", license = 
@License(name = "Apache 2.0", url = "http://foo.bar"), contact = 
@Contact(url = "http://gigantic-server.com", name = "Ricardo", email = "ricardo.baloira@atos.net")))
@Tag(name = "Proyectos")
@RestController
@RequestMapping("api/v1/projects")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Operation(summary="Get por ID", description="Getter de project" )
	@GetMapping("/{id}")
	public ResponseEntity<Project> getProject(@PathVariable("id") Long id) {

		Project projectEncontrado = projectService.findById(id);
		log.info("Proyecto encontrado");
		return new ResponseEntity<Project>(projectEncontrado, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> postProject(@RequestBody @Valid Project project, BindingResult result) {

		if (!result.hasErrors()) {
			Project projectNuevo = projectService.createProject(project);
			log.info("Proyecto creado");
			return new ResponseEntity<Project>(projectNuevo, HttpStatus.CREATED);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> putProject(@PathVariable("id") Long id, @RequestBody @Valid Project project,
			BindingResult result) {
		if (!result.hasErrors()) {
			projectService.updateProject(id, project);
			log.info("Proyecto modificado");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchProject(@PathVariable("id") Long id, @RequestBody Project project) {
		projectService.updateProject(id, project);
		log.info("Proyecto actualizado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
		Project projectBorrado = projectService.findById(id);
		projectService.deleteProject(projectBorrado);
		log.info("Proyecto eliminado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Project>> getProjects() {
		List<Project> projects = projectService.getAllProjects();
		log.info("Lista de proyectos mostrada");
		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}

	@PutMapping("/{id_project}/users")
	public ResponseEntity<Project> addUserToProject(@PathVariable("id_project") Long idProject,
			@RequestBody List<Long> idUsers) {

		return new ResponseEntity<Project>(projectService.addUsertoProject(idProject, idUsers), HttpStatus.OK);

	}

}
