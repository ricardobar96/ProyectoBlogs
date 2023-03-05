package net.atos.rest.proyecto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "PROJECTS")
public class Project implements Serializable {
	
private static final long serialVersionUID = 7825200298988382235L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROJECT_ID")
	private Long id;
	
	@NotBlank(message="El project debe tener una description")
	private String description;
	
	@NotBlank(message = "El blog debe tener un language")
	private String language;
	
	private boolean open;
	
	
	@ManyToMany
	@JoinTable(name = "PROJECT_USER", joinColumns = @JoinColumn(name = "PROJECT_ID"),inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private List<User> users = new ArrayList<User>();

}
