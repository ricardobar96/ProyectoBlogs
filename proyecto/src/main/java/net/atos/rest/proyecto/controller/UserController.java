package net.atos.rest.proyecto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import net.atos.rest.proyecto.model.User;
import net.atos.rest.proyecto.service.UserService;

@Slf4j
@OpenAPIDefinition(info = 
@Info(title = "API REST", version = "0.1", description = "API REST", license = 
@License(name = "Apache 2.0", url = "http://foo.bar"), contact = 
@Contact(url = "http://gigantic-server.com", name = "Ricardo", email = "ricardo.baloira@atos.net")))
@Tag(name = "Usuarios")
@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id){
		User userEncontrado = userService.findById(id);	
		log.info("Usuario encontrado");
		return new ResponseEntity<User>(userEncontrado, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody @Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			User userNuevo = userService.createUser(user);	
			log.info("Usuario creado");
			return new ResponseEntity<User>(userNuevo, HttpStatus.CREATED);
		} else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user", content = {
           @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<Void> putUser(@PathVariable("id") Long id, @RequestBody @Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			userService.updateUser(id, user);
			log.info("Usuario modificado");
			return new ResponseEntity<Void>(HttpStatus.OK);	
		}
		else {
			log.error("Error: " + result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
			throw new CustomException(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);	
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchUser(@PathVariable("id") Long id, @RequestBody User user){
		userService.updateUser(id, user);
		log.info("Usuario modificado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
		User userBorrado = userService.findById(id);
		userService.deleteUser(userBorrado);
		log.info("Usuario eliminado");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(){
		List<User> users = userService.getAllUsers();
		log.info("Lista de usuarios mostrada");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/page/{page}")
	public Page<User> getUsers(@PathVariable Integer page){
		return userService.getAllUsers(PageRequest.of(page, 4));
	}
}
