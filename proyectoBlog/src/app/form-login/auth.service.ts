import { Injectable } from '@angular/core';
import { User } from '../users/user';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser: any;
  private userCheck:any;
  private currentRole: any;

  private users = [
    {username: 'Ricardo', password: '12345678', roles: ['ADMIN', 'USER']},
    {username: 'usuario', password: '12345678', roles: ['USER']},
  ];

  constructor(private router: Router) { 
    let storage = localStorage.getItem('currentUser');
    if(storage) this.currentUser = JSON.parse(storage);
  }

  login(username: string, password: string): any {
    this.currentUser = this.users.find(
      (u) => u.username == username && u.password == password
    );

    localStorage.setItem('currentUser', JSON.stringify(this.currentUser));

    if(localStorage.getItem('currentUser')?.split(',')[2].includes('ADMIN')) {
      this.currentRole = 'ADMIN';    
      localStorage.setItem('role', JSON.stringify('ADMIN'));
    }

    else {
      this.currentRole = 'USER';
      localStorage.setItem('role', JSON.stringify('USER'));
    }

    this.userCheck =  localStorage.getItem('currentUser');
    
    return this.currentUser;
  }

  logout() {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿quieres cerrar sesion?',
      text: `¿Seguro que deseas cerrar sesion ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Logout',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.currentUser = null;
        localStorage.clear();
        this.userCheck = undefined;
        this.router.navigate(['/login']);
      }
    })
  }

  isLoggedIn():boolean {
    if(localStorage.getItem('currentUser')?.split(',')[2].includes('ADMIN') || 
    localStorage.getItem('currentUser')?.split(',')[2].includes('USER')){
      return true;
    }
      return false;
  }

}