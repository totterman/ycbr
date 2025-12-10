import InspectionPage from "@/inspections/InspectionPage";
import { inspectionQueryOptions } from "@/inspections/inspection";
import { createFileRoute, Link, useParams } from "@tanstack/react-router";

export const Route = createFileRoute("/inspect/$inspectionId")({
  loader: ({ context: { queryClient }, params: { inspectionId } }) => queryClient.ensureQueryData(inspectionQueryOptions(Number(inspectionId))),
  component: InspectionPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on the Inspection route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
});


