import { Blog } from "../blogs/blog";

export class Article {
    id: number;
    title: string;
    summary: string;
    content: string;
    blog: Blog;
}