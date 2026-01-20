import { createFileRoute, Link } from "@tanstack/react-router";
import BoatsPage from "@/boats/BoatsPage";
import { boatsQueryOptions } from "@/boats/boat";

export const Route = createFileRoute('/boats')({
  loader: ({ context: { queryClient } }) =>
    queryClient.ensureQueryData(boatsQueryOptions),
  component: BoatsPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on Boats route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on Boats route</p>
        <Link to="/inspect">Try again</Link>
      </div>
    );
  },
});
