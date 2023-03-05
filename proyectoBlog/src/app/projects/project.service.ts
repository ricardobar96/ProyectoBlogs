import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { of, Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Project } from './project';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private urlEndpoint: string = 'http://localhost:8080/api/v1/projects';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private  http: HttpClient, private router: Router) { }

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.urlEndpoint).pipe(
      map( response => {
        let projects = response as Project[];
        return projects.map(project => {
          return project;
        });
      }
      ))
  }

  create(project: Project) : Observable<Project> {
    return this.http.post<Project>(this.urlEndpoint, project, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.project as Project),
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        Swal.fire('Error al crear project', 'No ha sido posible insertar el project en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  getProject(id: any): Observable<Project> {
    return this.http.get<Project>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/projects']);
        Swal.fire('Error al editar', 'No existe el project en la base de datos', 'error');
        return throwError(e);
      })
    );
  }

  update(project: Project): Observable<Project> {
    return this.http.put<Project>(`${this.urlEndpoint}/${project.id}`, project, {headers: this.httpHeaders}).pipe(
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }
        
        Swal.fire('Error al editar project', 'No ha sido posible editar el project en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  delete(id: any): Observable<Project> {
    return this.http.delete<Project>(`${this.urlEndpoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire('Error al eliminar project', 'No ha sido eliminar el project de la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }
}
