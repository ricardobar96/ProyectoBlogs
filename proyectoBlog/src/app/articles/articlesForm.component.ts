import { Component, OnInit } from '@angular/core';
import { ArticleService } from './article.service';
import { Router, ActivatedRoute } from '@angular/router'
import { Article } from './article';
import swal from 'sweetalert2';
import { Blog } from '../blogs/blog';
import { BlogService } from '../blogs/blog.service';

@Component({
  selector: 'app-form',
  templateUrl: './articlesForm.component.html'
})
export class ArticlesFormComponent implements OnInit{
  
  public article: Article = new Article();
  public titulo: String = "Crear article";

  protected errores: String[];
  public blogs: Blog[];

  constructor(private articleService: ArticleService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public blogService: BlogService) {}

  public create(): void {
    this.articleService.create(this.article)
    .subscribe(article => {
      this.router.navigate(['/articles'])
      swal.fire('Nuevo article', `Article ${this.article.title} creado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  cargarArticle(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params ['id'];
      if(id) {
        this.articleService.getArticle(id).subscribe(
          (article) => this.article = article
        )
      }
    })
  }

  public update(): void {
    this.articleService.update(this.article)
    .subscribe(article => {
      this.router.navigate(['/articles'])
      swal.fire('Article actualizado', `Article ${this.article.title} se ha actualizado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  ngOnInit(): void {
    this.cargarArticle();
    this.blogService.getBlogs().subscribe(blogs => this.blogs = blogs);
  }
}