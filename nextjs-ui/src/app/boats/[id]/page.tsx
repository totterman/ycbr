import { Boat } from "../boat";
import { Person } from "../person";
import { ViewBoatDetails } from "@/src/components/BoatDetails";

export default function ViewBoatPage( id: number ) {
    const fixowner: Person = {
        username: "ville",
        firstname: "Ville",
        lastname: "Viseamiral"
    };
    const fixboat: Boat = {
        id: 123,
        name: "Sincabel",
        make: "Fleming",
        model: "60",
        year: "1995",
        loa_ft: 58,
        owner: fixowner
    };
    return (
        <ViewBoatDetails
         boat = {fixboat}
         />
    );
}