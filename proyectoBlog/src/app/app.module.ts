import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { RouterModule, Routes } from '@angular/router';
import { UserService } from './users/user.service';
import { HttpClientModule } from '@angular/common/http';
import { FormComponent } from './users/form.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArticlesComponent } from './articles/articles.component';
import { ArticlesFormComponent } from './articles/articlesForm.component';
import { ArticleService } from './articles/article.service';
import { BlogsComponent } from './blogs/blogs.component';
import { BlogsFormComponent } from './blogs/blogsForm.component';
import { BlogService } from './blogs/blog.service';
import localeEs from '@angular/common/locales/es'
import { registerLocaleData } from '@angular/common';
import { PaginatorComponent } from './paginator/paginator.component';
import { FormLoginComponent } from './form-login/form-login.component';
import { CanActivateGuard } from './guards/can-activate.guard';
import { ProjectsComponent } from './projects/projects.component';
import { ProjectService } from './projects/project.service';
import { ProjectsFormComponent } from './projects/projectsForm.component';

registerLocaleData(localeEs, 'es');

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full', },
  {path: 'users', component: UsersComponent, canActivate: [CanActivateGuard]},
  {path: 'users/form', component: FormComponent, canActivate: [CanActivateGuard]},
  {path: 'users/form/:id', component: FormComponent, canActivate: [CanActivateGuard]},
  {path: 'articles', component: ArticlesComponent, canActivate: [CanActivateGuard]},
  {path: 'articles/form', component: ArticlesFormComponent, canActivate: [CanActivateGuard]},
  {path: 'articles/form/:id', component: ArticlesFormComponent, canActivate: [CanActivateGuard]},
  {path: 'blogs', component: BlogsComponent, canActivate: [CanActivateGuard]},
  {path: 'blogs/form', component: BlogsFormComponent, canActivate: [CanActivateGuard]},
  {path: 'blogs/form/:id', component: BlogsFormComponent, canActivate: [CanActivateGuard]},
  {path: 'projects', component: ProjectsComponent, canActivate: [CanActivateGuard]},
  {path: 'projects/form', component: ProjectsFormComponent, canActivate: [CanActivateGuard]},
  {path: 'projects/form/:id', component: ProjectsFormComponent, canActivate: [CanActivateGuard]},
  {path: 'login', component: FormLoginComponent},
  {path: 'users/page/:page', component: UsersComponent, canActivate: [CanActivateGuard]},
  
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    UsersComponent,
    FormComponent,
    ArticlesComponent,
    ArticlesFormComponent,
    BlogsComponent,
    BlogsFormComponent,
    PaginatorComponent,
    FormLoginComponent,
    ProjectsComponent,
    ProjectsFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    UserService,
    ArticleService,
    BlogService,
    ProjectService,
    CanActivateGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
