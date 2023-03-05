import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { of, Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Article } from './article';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private urlEndpoint: string = 'http://localhost:8080/api/v1/articles';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private  http: HttpClient, private router: Router) { }

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.urlEndpoint).pipe(
      map( response => {
        let articles = response as Article[];
        return articles.map(article => {
          return article;
        });
      }
      ))
  }

  create(article: Article) : Observable<Article> {
    return this.http.post<Article>(this.urlEndpoint, article, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.article as Article),
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        Swal.fire('Error al crear article', 'No ha sido posible insertar el article en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  getArticle(id: any): Observable<Article> {
    return this.http.get<Article>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/articles']);
        Swal.fire('Error al editar', 'No existe el article en la base de datos', 'error');
        return throwError(e);
      })
    );
  }

  update(article: Article): Observable<Article> {
    return this.http.put<Article>(`${this.urlEndpoint}/${article.id}`, article, {headers: this.httpHeaders}).pipe(
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }
        
        Swal.fire('Error al editar article', 'No ha sido posible editar el article en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  delete(id: any): Observable<Article> {
    return this.http.delete<Article>(`${this.urlEndpoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire('Error al eliminar article', 'No ha sido eliminar el article de la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }
}
