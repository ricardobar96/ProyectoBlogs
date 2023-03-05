package net.atos.rest.proyecto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "BLOGS")
public class Blog implements Serializable {

	private static final long serialVersionUID = 5101822209118673778L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BLOG_ID")
	private Long id;

	@NotBlank(message = "El blog debe tener un titulo")
	@Column(name="TITLE")
	private String title;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE")
	private Date date;

	@PrePersist
	public void prePersist() {
		date = new Date();
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@OneToMany(mappedBy = "blog")
	@JsonIgnore
	List<Article> articles = new ArrayList<Article>();

}
