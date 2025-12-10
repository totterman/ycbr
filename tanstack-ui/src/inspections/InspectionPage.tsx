import { useSuspenseQuery } from "@tanstack/react-query";
import { getRouteApi } from "@tanstack/react-router";
import { inspectionQueryOptions } from "./inspection";
import InspectionStepper from "./InspectionStepper";
import { Card } from "./form/Card";
import { CardContent, Grid2, Typography } from "@mui/material";
import { useGetBoat } from "@/boats/boat";
import { useGetI9Event } from "@/inspectionevents/inspectionevent";
import dayjs from "dayjs";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";

export default function InspectionPage() {
  const route = getRouteApi("/inspect/$inspectionId");
  const id = Number(route.useParams().inspectionId);
  const { data, isLoading, refetch } = useSuspenseQuery(
    inspectionQueryOptions(id)
  );
  const content = useIntlayer("inspections");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const boat = useGetBoat(data.boat.toString());
  const i9event = useGetI9Event(data.event.toString());

  if (isLoading) return <p>Loading..</p>;

  return (
    <>
    <Grid2
        container
        spacing={2}
        columns={12}
        sx={{ mb: (theme: { spacing: (arg0: number) => any; }) => theme.spacing(2) }}
      >
      <Grid2 size={{ xs: 12, md: 6 }}>
        <Card variant="outlined" sx={{ width: '100%' }}>
          <CardContent>
            <Typography
              gutterBottom
              sx={{ color: "text.secondary", fontSize: 14 }}
            >
              {content.inspected_boat}
            </Typography>
            <Typography variant="h5" component="div">
              {boat?.name}
            </Typography>
            <Typography sx={{ color: "text.secondary", mb: 1.5 }}>
              {boat?.year} {boat?.make} {boat?.model}
            </Typography>
            <Typography variant="body2">
              {content.owner}: {boat?.owner}
              <br />
              {content.engine}: {boat?.engines}
            </Typography>
          </CardContent>
        </Card>
      </Grid2>
      <Grid2 size={{ xs: 12, md: 6 }}>
        <Card variant="outlined" sx={{ width: '100%' }}>
          <CardContent>
            <Typography
              gutterBottom
              sx={{ color: "text.secondary", fontSize: 14 }}
            >
              {content.inspection_event}
            </Typography>
            <Typography variant="h5" component="div">
              {i9event?.place}
            </Typography>
            <Typography sx={{ color: "text.secondary", mb: 1.5 }}>
              {dayjs(i9event?.day).toDate().toLocaleDateString(tlds)}
            </Typography>
            <Typography variant="body2">
              {content.inspector}: {data.inspector}
              <br />
              {content.started}: {dayjs(data.timestamp).toDate().toLocaleTimeString(locale.substring(0, 2))}
            </Typography>
          </CardContent>
        </Card>
      </Grid2>
      </Grid2>
      <InspectionStepper data={data} />
    </>
  );
}
