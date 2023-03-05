import { User } from "../users/user";

export class Project {
    id: number;
    description: string;
    language: string;
    open: boolean;
    users: User[];
}