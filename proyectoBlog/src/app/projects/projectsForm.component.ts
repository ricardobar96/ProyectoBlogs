import { Component, OnInit } from '@angular/core';
import { ProjectService } from './project.service';
import { Router, ActivatedRoute } from '@angular/router'
import { Project } from './project';
import swal from 'sweetalert2';
import { User } from '../users/user';
import { UserService } from '../users/user.service';

@Component({
  selector: 'app-form',
  templateUrl: './projectsForm.component.html'
})
export class ProjectsFormComponent implements OnInit{
  
  public users: User[];
  public project: Project = new Project();
  public titulo: String = "Crear project";

  protected errores: String[];

  constructor(private projectService: ProjectService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public userService: UserService) {}

  public create(): void {
    this.projectService.create(this.project)
    .subscribe(project => {
      this.router.navigate(['/projects'])
      swal.fire('Nuevo project', `Project ${this.project.description} creado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  cargarProject(): void {
    this.activatedRoute.params.subscribe(params => {
      let id = params ['id'];
      if(id) {
        this.projectService.getProject(id).subscribe(
          (project) => this.project = project
        )
      }
    })
  }

  public update(): void {
    this.projectService.update(this.project)
    .subscribe(project => {
      this.router.navigate(['/projects'])
      swal.fire('Project actualizado', `Project ${this.project.description} se ha actualizado con exito`, 'success')
    },
    err => {
      this.errores = err.error.errors as string[];
      console.error(err.error.errors); 
    }
    );
  }

  ngOnInit(): void {
    this.cargarProject();
    this.userService.getUsersAll().subscribe(users => this.users = users);
  }
}