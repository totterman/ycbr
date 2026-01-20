import { createFileRoute, Link } from "@tanstack/react-router";
import "dayjs/locale/sv";
import "dayjs/locale/fi";
import "dayjs/locale/en";
import { completeQueryOptions } from "@/inspectionevents/inspectionevent";
import DispatchPage from "@/inspections/DispatchPage";

export const Route = createFileRoute("/dispatch")({
  loader: ({ context: { queryClient } }) =>
    queryClient.ensureQueryData(completeQueryOptions),
  component: DispatchPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on dispatch route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on dispatch route</p>
        <Link to="/inspect">Try again</Link>
      </div>
    );
  },
});
