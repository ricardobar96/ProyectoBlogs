package net.atos.rest.proyecto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements Serializable{
	
	private static final long serialVersionUID = 7825200298988382235L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long id;
	

	@NotBlank(message="El user debe tener un nombre")
	private String nombre;
	
	private String nick;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs= new ArrayList<Blog>();
	
	@JsonIgnore
	@ManyToMany(mappedBy="users")
	private List<Project> projects = new ArrayList<Project>();

}
	
