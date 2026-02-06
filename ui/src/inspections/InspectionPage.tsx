import { useSuspenseQuery } from "@tanstack/react-query";
import { getRouteApi } from "@tanstack/react-router";
import { inspectionQueryOptions } from "./inspection";
import InspectionStepper from "./InspectionStepper";
import { Card } from "./form/Card";
import {
  CardContent,
  FormControl,
  Grid2,
  MenuItem,
  Select,
  SelectChangeEvent,
  Stack,
  Typography,
} from "@mui/material";
import { useGetBoat } from "@/boats/boat";
import { useGetI9Event } from "@/inspectionevents/inspectionevent";
import dayjs from "dayjs";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";
import { useContext, useState } from "react";
import { Category, CategoryContext } from "./categorycontext";

export default function InspectionPage() {
  const route = getRouteApi("/inspect/$inspectionId");
  const id = route.useParams().inspectionId;
  const { data, isLoading, refetch } = useSuspenseQuery(
    inspectionQueryOptions(id),
  );
  const content = useIntlayer("inspections");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const boat = useGetBoat(data.boatId);
  const i9event = useGetI9Event(data.eventId);

  const classes = [
//    { code: "0", title: content.undefined },
    { code: "1", title: content.offshore },
    { code: "2", title: content.coastal },
    { code: "3", title: content.inshore },
    { code: "4", title: content.protected_waters },
  ];

  const [inspectionClass, setInspectionClass] = useState<string>("1");

  const handleChange = (event: SelectChangeEvent) => {
    setInspectionClass(event.target.value);
    console.log("Inspection Class:", inspectionClass);
  };

  const category: Category = {
    kind: boat?.kind || "S",
    inspectionClass: inspectionClass,
  };

  if (isLoading) return <p>Loading..</p>;

  return (
    <>
      <Grid2
        container
        spacing={2}
        columns={12}
        sx={{
          mb: (theme: { spacing: (arg0: number) => any }) => theme.spacing(2),
        }}
      >
        <Grid2 size={{ xs: 12, md: 6 }}>
          <Card variant="outlined" sx={{ width: "100%" }}>
            <CardContent>
              <Typography
                gutterBottom
                sx={{ color: "text.secondary", fontSize: 14 }}
              >
                {boat?.kind === "M" ? content.motor_boat.value : content.sail_boat.value}
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
          <Card variant="outlined" sx={{ width: "100%" }}>
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
                {content.inspector}: {data.inspectorName}
                <br />
                {content.started}:{" "}
                {dayjs(data.timestamp)
                  .toDate()
                  .toLocaleTimeString(locale.substring(0, 2))}
              </Typography>
            </CardContent>
          </Card>
        </Grid2>
      </Grid2>

      <Stack
        direction="row"
        spacing={4}
        sx={{ justifyContent: "flex-start", alignItems: "center", mt: 4 }}
      >
        <Typography variant="h6">{content.inspection_class.value}</Typography>
        <FormControl variant="standard" required sx={{ m: 1, minWidth: 120 }}>
          <Select label="INSPECTION_CLASS" value={inspectionClass}>
            {classes.map((cl) => (
              <MenuItem
                value={cl.code}
                onClick={() => setInspectionClass(cl.code)}
              >
                {cl.code} {cl.title}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Stack>

      <CategoryContext value={category}>
        <InspectionStepper data={data} />
      </CategoryContext>
    </>
  );
}
