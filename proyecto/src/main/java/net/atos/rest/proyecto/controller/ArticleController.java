package net.atos.rest.proyecto.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Article;
import net.atos.rest.proyecto.service.ArticleService;

@Slf4j
@OpenAPIDefinition(info = 
@Info(title = "API REST", version = "0.1", description = "API REST", license = 
@License(name = "Apache 2.0", url = "http://foo.bar"), contact = 
@Contact(url = "http://gigantic-server.com", name = "Ricardo", email = "ricardo.baloira@atos.net")))
@Tag(name = "Articulos")
@RestController
@RequestMapping("/api/v1/articles")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Operation(summary = "Obtener información del articulo por ID")
	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticle(@PathVariable("id") Long id) {
		Article articleEncontrado = articleService.findById(id);
		log.info("Articulo encontrado");
		return new ResponseEntity<Article>(articleEncontrado, HttpStatus.OK);
	}
	
	@Operation(summary = "Inserta un nuevo articulo")
	@PostMapping
	public ResponseEntity<?> postArticle(@RequestBody @Valid Article article, BindingResult result) {
		if (!result.hasErrors()) {
			
			Article articleNuevo = articleService.createArticle(article);
			log.info("Articulo creado");
			return new ResponseEntity<Article>(articleNuevo, HttpStatus.CREATED);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Actualizar información del articulo", description = "This can only be done by the logged in user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the article", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Article.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid username supplied"),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<Void> putArticle(@PathVariable("id") Long id, @RequestBody @Valid Article article,
			BindingResult result) {
		if (!result.hasErrors()) {
			articleService.updateArticle(id, article);
			log.info("Articulo modificado");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Actualizar información del articulo")
	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchArticle(@PathVariable("id") Long id, @RequestBody Article article) {
		articleService.updateArticle(id, article);
		log.info("Articulo modificado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@Operation(summary = "Elimina un articulo")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
		Article articleBorrado = articleService.findById(id);
		articleService.deleteArticle(articleBorrado);
		log.info("Articulo eliminado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@Operation(summary = "Listado de articulos")
	@GetMapping
	public ResponseEntity<List<Article>> getArticles() {
		List<Article> articles = articleService.getAllArticles();
		log.info("Lista de articulos mostrada");
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}
	
	@PutMapping("/{id_article}/blog/{id_blog}")
	public ResponseEntity<Article> addBlogToArticle(
			@Parameter(description = "Para ", required = true) @PathVariable("id_article") Long id_article,
			@PathVariable("id_blog") Long id_blog) {

		return new ResponseEntity<Article>(articleService.addArticleToBlog(id_article, id_blog), HttpStatus.OK);

	}
}
