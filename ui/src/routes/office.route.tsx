import OfficePage from "@/office/OfficePage";
import { createFileRoute, Link } from "@tanstack/react-router";

export const Route = createFileRoute("/office")({
  component: OfficePage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on office route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on office route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },});
