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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Blog;
import net.atos.rest.proyecto.service.BlogService;

@Slf4j
@OpenAPIDefinition(info = 
@Info(title = "API REST", version = "0.1", description = "API REST", license = 
@License(name = "Apache 2.0", url = "http://foo.bar"), contact = 
@Contact(url = "http://gigantic-server.com", name = "Ricardo", email = "ricardo.baloira@atos.net")))
@Tag(name = "Blogs")
@RestController
@RequestMapping("api/v1/blogs")
@CrossOrigin(origins = { "http://localhost:4200" })
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping("/{id}")
	public ResponseEntity<Blog> getBlog(@PathVariable("id") Long id) {

		Blog blogEncontrado = blogService.findById(id);
		log.info("Blog encontrado");
		return new ResponseEntity<Blog>(blogEncontrado, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> postBlog(@RequestBody @Valid Blog blog, BindingResult result) {

		if (!result.hasErrors()) {

			Blog blogNuevo = blogService.createBlog(blog);
			log.info("Blog creado");
			return new ResponseEntity<Blog>(blogNuevo, HttpStatus.CREATED);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> putBlog(@PathVariable("id") Long id, @RequestBody @Valid Blog blog,
			BindingResult result) {
		if (!result.hasErrors()) {
			blogService.updateBlog(id, blog);
			log.info("Blog modificado");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchUser(@PathVariable("id") Long id, @RequestBody Blog blog) {
		blogService.updateBlog(id, blog);
		log.info("Blog modificado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBlog(@PathVariable("id") Long id) {
		Blog blogBorrado = blogService.findById(id);
		blogService.deleteBlog(blogBorrado);
		log.info("Blog eliminado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Blog>> getBlogs() {
		List<Blog> blogs = blogService.getAllBlogs();
		log.info("Lista de blogs mostrada");
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}

	@PutMapping("/{id_blog}/user/{id_user}")
	public ResponseEntity<Blog> addUserToBlog(
			@Parameter(description = "Para ", required = true) @PathVariable("id_blog") Long id_blog,
			@PathVariable("id_user") Long id_user) {

		return new ResponseEntity<Blog>(blogService.addUserToBlog(id_blog, id_user), HttpStatus.OK);

	}

}
