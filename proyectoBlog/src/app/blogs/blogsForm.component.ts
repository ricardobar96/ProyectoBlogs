import { Component, OnInit } from '@angular/core';
import { BlogService } from './blog.service';
import { Router, ActivatedRoute } from '@angular/router'
import { Blog } from './blog';
import swal from 'sweetalert2';
import { User } from '../users/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserService } from '../users/user.service';

@Component({
  selector: 'app-form',
  templateUrl: './blogsForm.component.html'
})
export class BlogsFormComponent implements OnInit{
  
  public blog: Blog = new Blog();
  public titulo: String = "Crear blog";

  //declaracion lista users para poder presentar users existentes en select dentro del form de blog
  public users: User[];

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})
  protected errores: String[];

  //declaracion de userService en el constructor para poder hacer uso de getUsers()
  constructor(private blogService: BlogService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private  http: HttpClient,
    public userService: UserService) {}

  public create(): void {
    this.blogService.create(this.blog)
    .subscribe(blog => {
      this.router.navigate(['/blogs'])
      swal.fire('Nuevo blog', `Blog ${this.blog.title} creado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  cargarBlog(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params ['id'];
      if(id) {
        this.blogService.getBlog(id).subscribe(
          (blog) => this.blog = blog
        )
      }
    })
  }

  public update(): void {
    this.blogService.update(this.blog)
    .subscribe(blog => {
      this.router.navigate(['/blogs'])
      swal.fire('Blog actualizado', `Blog ${this.blog.title} se ha actualizado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  ngOnInit(): void {
    this.cargarBlog();
    this.userService.getUsersAll().subscribe(users => this.users = users);
    //llamada a todos los usuarios existentes para guardarlo en list users por medio de userService
  }
}