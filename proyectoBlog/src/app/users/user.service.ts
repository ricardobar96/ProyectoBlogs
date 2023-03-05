import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { of, Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from './user';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private urlEndpoint: string = 'http://localhost:8080/api/v1/users';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})
  constructor(private  http: HttpClient, private router: Router) { }

  getUsers(page:number): Observable<any> {
    return this.http.get(this.urlEndpoint + "/page/" + page).pipe(
      map( (response:any) => {
        (response.content as User[]).map(user => {
          return user;
        });
        return response;
      }
      ))
  }

  getUsersAll(): Observable<User[]> {
    return this.http.get<User[]>(this.urlEndpoint).pipe(
      map( response => {
        let users = response as User[];
        return users.map(user => {
          return user;
        });
      }
      ))
  }

  create(user: User) : Observable<User> {
    return this.http.post<User>(this.urlEndpoint, user, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.user as User),
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        Swal.fire('Error al crear user', 'No ha sido posible insertar el user en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  getUser(id: any): Observable<User> {
    return this.http.get<User>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/users']);
        Swal.fire('Error al editar', 'No existe el user en la base de datos', 'error');
        return throwError(e);
      })
    );
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${this.urlEndpoint}/${user.id}`, user, {headers: this.httpHeaders}).pipe(
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }
        
        Swal.fire('Error al editar user', 'No ha sido posible editar el user en la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }

  delete(id: any): Observable<User> {
    return this.http.delete<User>(`${this.urlEndpoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        Swal.fire('Error al eliminar user', 'No ha sido eliminar el user de la base de datos', 'error');
        return throwError(e);
      })
    ); 
  }
}
