import { Boat } from "../app/boats/boat";

export function ViewBoatDetails({ boat }: { boat: Boat }) {
    return (
        <article>
            <h3>{boat.name}</h3>
            <div>{boat.make} {boat.model} {boat.year}</div>
        </article>
    );
}