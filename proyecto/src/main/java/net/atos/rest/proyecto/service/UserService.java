package net.atos.rest.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.User;
import net.atos.rest.proyecto.repository.UserRepository;


@Service
public class UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}
	@Transactional (readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	//Transaccion que lleva a cabo la paginacion de usuarios
	@Transactional (readOnly = true)
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	@Transactional (readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(()->new CustomException("El usuario no existe", HttpStatus.BAD_REQUEST));
	}
	@Transactional
	public User updateUser(Long id, User user) {
		
		User userExistente = findById(id);
		
		if (user.getNick() != null)
			userExistente.setNick(user.getNick());
		if (user.getNombre() != null)
			userExistente.setNombre(user.getNombre());
		
		return userRepository.save(userExistente);
	}
	@Transactional (readOnly = true)
	public List<User> getAllUsersById(List <Long> usersId){
		
	
		return userRepository.findAllById(usersId);
		
		
	}
}
