import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Project } from './project';
import { Subscription } from 'rxjs';
import { ProjectService } from './project.service';
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { User } from '../users/user';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  protected role: any = JSON.parse(localStorage.getItem('role')!);

  public users: User[];
  projects!: Project[];
  paginador: any;
  projectSubscribe: Subscription;
   
  constructor(private projectService: ProjectService, private changeDetectorRef: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute){ 
  }

  ngOnInit(): void {
    this.projectSubscribe =
    this.projectService.getProjects().pipe()
    .subscribe(
      (projects) => {
        this.projects = projects
      }
    );
  }

  delete(project: Project) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿Estas seguro?',
      text: `¿Seguro que deseas eliminar al cliente ${project.description} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.projectService.delete(project.id).subscribe(
          response => {
            this.projects = this.projects.filter (
              cli => cli !== project
            )
            swalWithBootstrapButtons.fire(
              'Project eliminado',
              `Project ${project.description} eliminado con exito`,
              'success'
            )
          }
        )
      }
    })
  }

}
