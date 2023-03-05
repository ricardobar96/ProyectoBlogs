import { User } from "../users/user";

export class Blog {
    id: number;
    title: string;
    date: Date | any;
    user: User;
}