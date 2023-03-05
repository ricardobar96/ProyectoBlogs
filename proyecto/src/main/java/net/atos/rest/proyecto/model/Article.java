package net.atos.rest.proyecto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ARTICLES")
public class Article implements Serializable{

	private static final long serialVersionUID = 8706051435182991343L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ARTICLE_ID")
	private Long id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="SUMMARY")
	private String summary;
	
	@Column(name="CONTENT")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="BLOG_ID")	
	private Blog blog;

}
