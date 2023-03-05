import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { Router, ActivatedRoute } from '@angular/router'
import { User } from './user';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit{
  
  public user: User = new User();
  public titulo: String = "Crear user";

  protected errores: String[];

  constructor(private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {}

  public create(): void {
    this.userService.create(this.user)
    .subscribe(user => {
      this.router.navigate(['/users'])
      swal.fire('Nuevo user', `User ${this.user.nombre} creado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  cargarUser(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params ['id'];
      if(id) {
        this.userService.getUser(id).subscribe(
          (user) => this.user = user
        )
      }
    })
  }

  public update(): void {
    this.userService.update(this.user)
    .subscribe(user => {
      this.router.navigate(['/users'])
      swal.fire('User actualizado', `User ${this.user.nombre} se ha actualizado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  ngOnInit(): void {
    this.cargarUser()
  }
}