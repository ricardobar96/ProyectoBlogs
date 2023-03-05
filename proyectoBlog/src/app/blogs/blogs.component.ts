import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Blog } from './blog';
import { Subscription } from 'rxjs';
import { BlogService } from './blog.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.css']
})
export class BlogsComponent implements OnInit {

  protected role: any = JSON.parse(localStorage.getItem('role')!);
  
  blogs!: Blog[];
  blogSubscribe: Subscription;
  constructor(private blogService: BlogService, private changeDetectorRef: ChangeDetectorRef){ 
  }

  ngOnInit(): void {
    this.blogSubscribe =
    this.blogService.getBlogs().pipe()
    .subscribe(
      (blogs) => {
        this.blogs = blogs
      }
    );
  }

  delete(blog: Blog) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿Estas seguro?',
      text: `¿Seguro que deseas eliminar al blog ${blog.title} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.blogService.delete(blog.id).subscribe(
          response => {
            this.blogs = this.blogs.filter (
              cli => cli !== blog
            )
            swalWithBootstrapButtons.fire(
              'Blog eliminado',
              `Blog ${blog.title} eliminado con exito`,
              'success'
            )
          }
        )
      }
    })
  }

}