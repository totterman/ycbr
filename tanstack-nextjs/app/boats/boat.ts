import { Person } from "./person";

export interface Boat {
    id: number;
    name: string;
    make: string;
    model: string;
    year: string;
    loa_ft: number;
    owner: Person;
}