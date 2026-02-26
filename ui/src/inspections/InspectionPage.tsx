import { useSuspenseQuery } from "@tanstack/react-query";
import { getRouteApi } from "@tanstack/react-router";
import { inspectionQueryOptions } from "./inspection";
import InspectionStepper from "./InspectionStepper";
import { Card } from "./form/Card";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  CardContent,
  FormControl,
  Grid2,
  MenuItem,
  Select,
  SelectChangeEvent,
  Stack,
  Typography,
} from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useGetBoat } from "@/boats/boat";
import { useGetI9Event } from "@/inspectionevents/inspectionevent";
import dayjs from "dayjs";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";
import { useState } from "react";
import { Category, CategoryContext } from "./categorycontext";
import { BoatDetailPanel } from "@/boats/BoatDetailPanel";

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

  /* ************************************************ **
   *
   * Inspection category
   *
   * ************************************************ */

  const classes = [
    //    { code: "0", title: content.undefined },
    { code: "1", title: content.offshore },
    { code: "2", title: content.coastal },
    { code: "3", title: content.inshore },
    { code: "4", title: content.protected_waters },
  ];

  const [inspectionClass, setInspectionClass] = useState<string>("1");

//  const handleChange = (event: SelectChangeEvent) => {
//    setInspectionClass(event.target.value);
//    console.log("Inspection Class:", inspectionClass);
//  };

  const category: Category = {
    kind: boat?.kind || "S",
    inspectionClass: inspectionClass,
  };

  if (isLoading) return <p>Loading..</p>;

  return (
    <>
    <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
        >
           <Typography component="span" sx={{ width: '33%', flexShrink: 0 }}>
            {boat?.name} {boat?.sign}
          </Typography>
          <Typography component="span" sx={{ color: 'text.secondary' }}>
            {i9event?.place} {dayjs(i9event?.day).toDate().toLocaleDateString(tlds)} {data.inspectorName} [{dayjs(data.timestamp).toDate().toLocaleTimeString(locale.substring(0, 2))}]
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          {!!boat && <BoatDetailPanel boat={boat} ro={true} />}
        </AccordionDetails>
      </Accordion>
      
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
