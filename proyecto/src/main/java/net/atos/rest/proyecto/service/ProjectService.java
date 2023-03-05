package net.atos.rest.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Project;
import net.atos.rest.proyecto.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	@Transactional
	public Project createProject(Project project) {
		return projectRepository.save(project);
	}
	@Transactional(readOnly = true)
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	@Transactional
	public void deleteProject(Project project) {
		projectRepository.delete(project);
	}
	
	public Project findById(Long id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new CustomException("El proyecto no existe", HttpStatus.BAD_REQUEST));
	}
	@Transactional
	public Project updateProject(Long id, Project project) {
	
		Project projectExistente= findById(id);
		
		if (project.getDescription() != null)
			projectExistente.setDescription(project.getDescription());
		if (project.getLanguage() != null)
			projectExistente.setLanguage(project.getLanguage());
		
		if(project.isOpen()) {
			projectExistente.setOpen(true);
		}
		else {
			projectExistente.setOpen(false);
		}
		
		if (project.getUsers() != null)
			projectExistente.setUsers(project.getUsers());
		
		return projectRepository.save(projectExistente);
	}
	
	@Transactional
	public Project addUsertoProject (Long idProject, List<Long> idUsers) {
		
		Project projectExistente= findById(idProject);
		
		projectExistente.getUsers().addAll(userService.getAllUsersById(idUsers));
		
		return createProject(projectExistente);	
	}

}
