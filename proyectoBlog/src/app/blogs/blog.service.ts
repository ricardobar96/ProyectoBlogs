import { Injectable } from '@angular/core';
import { Blog } from './blog';
import { of, Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { map, catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { DatePipe, registerLocaleData } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  private urlEndpoint: string = 'http://localhost:8080/api/v1/blogs';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private  http: HttpClient, private router: Router) { }

  getBlogs(): Observable<Blog[]> {
    return this.http.get<Blog[]>(this.urlEndpoint).pipe(
      map( response => {
        let blogs = response as Blog[];
        return blogs.map(blog => {
          
          let datePipe = new DatePipe('es');
          blog.date = datePipe.transform(blog.date, 'EEEE dd, MMMM yyyy');
          return blog;
        });
      }
      ),
    );
  }

  create(blog: Blog) : Observable<Blog> {
    return this.http.post<Blog>(this.urlEndpoint, blog, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.blog as Blog),
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        Swal.fire('Error al crear blog', 'No ha sido posible insertar el blog en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  getBlog(id: any): Observable<Blog> {
    return this.http.get<Blog>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/blogs']);
        Swal.fire('Error al editar', 'No existe el blog en la base de datos', 'error');
        return throwError(e);
      })
    );
  }

  update(blog: Blog): Observable<Blog> {
    return this.http.put<Blog>(`${this.urlEndpoint}/${blog.id}`, blog, {headers: this.httpHeaders}).pipe(
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }
        
        Swal.fire('Error al editar blog', 'No ha sido posible editar el blog en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  delete(id: any): Observable<Blog> {
    return this.http.delete<Blog>(`${this.urlEndpoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire('Error al eliminar blog', 'No ha sido eliminar insertar el blog de la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }
}