import InspectionsPage from "@/inspections/InspectionsPage";
import { createFileRoute, Link } from "@tanstack/react-router";

export const Route = createFileRoute("/inspect/")({
  component: InspectionsPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on inspect route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on inspect route</p>
        <Link to="/inspect">Try again</Link>
      </div>
    );
  },
});
