import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../form-login/auth.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CanActivateGuard implements CanActivate {

  constructor(private auth: AuthService, private _router:Router) {}
  
  canActivate(route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree { 
    
    let checkUser:boolean = this.auth.isLoggedIn();
    if(!checkUser){
      Swal.fire('Usuario no logueado', 'Inicia sesion para poder acceder a esta pagina', 'error');
      this._router.navigate(["/login"]);  
      return false;
    }   
   
    return true;
  }
  
}
