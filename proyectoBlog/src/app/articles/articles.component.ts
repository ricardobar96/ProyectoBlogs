import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Article } from './article';
import { Subscription } from 'rxjs';
import { ArticleService } from './article.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  protected role: any = JSON.parse(localStorage.getItem('role')!);
  
  articles!: Article[];
  articleSubscribe: Subscription;
  constructor(private articleService: ArticleService, private changeDetectorRef: ChangeDetectorRef){ 
  }

  ngOnInit(): void {
    this.articleSubscribe =
    this.articleService.getArticles().pipe()
    .subscribe(
      (articles) => {
        this.articles = articles
      }
    );
  }

  delete(article: Article) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿Estas seguro?',
      text: `¿Seguro que deseas eliminar el articulo ${article.title} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.articleService.delete(article.id).subscribe(
          response => {
            this.articles = this.articles.filter (
              cli => cli !== article
            )
            swalWithBootstrapButtons.fire(
              'Article eliminado',
              `Article ${article.title} eliminado con exito`,
              'success'
            )
          }
        )
      }
    })
  }

}