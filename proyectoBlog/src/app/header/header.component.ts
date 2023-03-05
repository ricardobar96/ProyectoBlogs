import { Component } from '@angular/core';
import { AuthService } from '../form-login/auth.service';

@Component({
    selector:'app-header',
    templateUrl: './header.component.html'
})

export class HeaderComponent {
    title:string = 'Proyecto Blog'

    protected role: any = JSON.parse(localStorage.getItem('role')!);
    
    constructor(
        protected auth: AuthService
      ) {}
}