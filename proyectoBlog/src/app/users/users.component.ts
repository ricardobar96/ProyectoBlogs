import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { User } from './user';
import { Subscription } from 'rxjs';
import { UserService } from './user.service';
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  protected role: any = JSON.parse(localStorage.getItem('role')!);

  users!: User[];
  paginador: any;
  userSubscribe: Subscription;
   
  constructor(private userService: UserService, private changeDetectorRef: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute){ 
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => { 
      let page: number = Number(params.get('page'));
     if(!page){
      page =0;
    }

    this.userService.getUsers(page).pipe()
    .subscribe(
      (response) => {
        this.users = response.content as User[];
        this.paginador = response;
      }
    );
    })
  }

  delete(user: User) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿Estas seguro?',
      text: `¿Seguro que deseas eliminar al cliente ${user.nombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.delete(user.id).subscribe(
          response => {
            this.users = this.users.filter (
              cli => cli !== user
            )
            swalWithBootstrapButtons.fire(
              'User eliminado',
              `User ${user.nombre} eliminado con exito`,
              'success'
            )
          }
        )
      }
    })
  }

}
