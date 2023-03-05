package net.atos.rest.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Blog;
import net.atos.rest.proyecto.model.User;
import net.atos.rest.proyecto.repository.BlogRepository;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional 
	public Blog addUserToBlog(Long id_blog, Long id_user) {
		
		User user = userService.findById(id_user);

		Blog blog = blogRepository.findById(id_blog).get();
		
		blog.setUser(user);
		
		return blogRepository.save(blog);
		
	}
	
	@Transactional 
	public Blog createBlog(Blog blog) {
		User user = userService.findById(blog.getUser().getId());	
		blog.setUser(user);
		
		return blogRepository.save(blog);
	}
	@Transactional (readOnly = true)
	public List<Blog> getAllBlogs() {
		return blogRepository.findAll();
	}
	@Transactional 
	public void deleteBlog(Blog blog) {
		blogRepository.delete(blog);
	}

	public Blog findById(Long id) {
		return blogRepository.findById(id).orElseThrow(()->new CustomException("El Blog no existe", HttpStatus.BAD_REQUEST));
	}
	
	public Blog updateBlog(Long id, Blog blog) {
		
		Blog blogExistente = findById(id);
		
		if (blog.getTitle() != null)
			blogExistente.setTitle(blog.getTitle());
		if (blog.getDate() != null)
			blogExistente.setDate(blog.getDate());
		if (blog.getUser() != null)
			blogExistente.setUser(blog.getUser());
		
		return blogRepository.save(blogExistente);
	}
	
}
