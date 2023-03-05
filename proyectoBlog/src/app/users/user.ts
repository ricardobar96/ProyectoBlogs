import { Blog } from "../blogs/blog";
import { Project } from "../projects/project";

export class User {
    id: number;
    nombre: string;
    nick: string;
    blogs: Blog[];
    projects: Project[];
}