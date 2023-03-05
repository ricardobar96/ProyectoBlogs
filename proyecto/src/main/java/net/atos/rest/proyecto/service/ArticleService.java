package net.atos.rest.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.rest.proyecto.exception.CustomException;
import net.atos.rest.proyecto.model.Article;
import net.atos.rest.proyecto.model.Blog;
import net.atos.rest.proyecto.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private BlogService blogService;
	
	@Transactional 
	public Article addArticleToBlog(Long id_blog, Long id_article) {

		Blog blog = blogService.findById(id_blog);

		Article article = articleRepository.findById(id_article).get();

		article.setBlog(blog);

		return articleRepository.save(article);

	}
	
	@Transactional
	public Article createArticle(Article article) {
		Blog blog = blogService.findById(article.getBlog().getId());

		article.setBlog(blog);

		return articleRepository.save(article);
	}
	@Transactional (readOnly = true)
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}
	@Transactional 
	public void deleteArticle(Article article) {
		articleRepository.delete(article);
	}
	@Transactional (readOnly = true)
	public Article findById(Long id) {
		return articleRepository.findById(id).orElseThrow(()->new CustomException("El usuario no existe", HttpStatus.BAD_REQUEST));
	}
	@Transactional
	public Article updateArticle(Long id, Article article) {
		
		Article articleExistente = findById(id);
		
		if (article.getTitle() != null)
			articleExistente.setTitle(article.getTitle());
		if (article.getSummary() != null)
			articleExistente.setSummary(article.getSummary());
		if (article.getContent() != null)
			articleExistente.setContent(article.getContent());
		if (article.getBlog() != null)
			articleExistente.setBlog(article.getBlog());
		
		return articleRepository.save(articleExistente);
	}
	
	
	
	
	
	
	
	
	
	
}
