import { createFileRoute } from "@tanstack/react-router";
import "dayjs/locale/sv";
import "dayjs/locale/fi";
import "dayjs/locale/en";
import { completeQueryOptions } from "@/inspectionevents/inspectionevent";
import DispatchPage from "@/inspections/DispatchPage";

export const Route = createFileRoute("/dispatch")({
  loader: ({ context: { queryClient } }) =>
    queryClient.ensureQueryData(completeQueryOptions),
  component: DispatchPage,
});
