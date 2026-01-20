import { createFileRoute, Link } from "@tanstack/react-router";
import { InspectionEventPage } from "@/inspectionevents/InspectionEventPage";

export const Route = createFileRoute("/i9event")({
  //  loader: ({ context: { queryClient } }) =>
  //      queryClient.ensureQueryData(ieQueryOptions),
  component: InspectionEventPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>
          This is the notFoundComponent configured on Inspection Events route
        </p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on Inspection Events route</p>
        <Link to="/inspect">Try again</Link>
      </div>
    );
  },
});
