import { useUser } from "@/auth/useUser";
import { useGetBoat } from "@/boats/boat";
import { useGetI9Event } from "@/inspectionevents/inspectionevent";
import { ListItemButton, ListItemText } from "@mui/material";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import { useNavigate } from "@tanstack/react-router";
import dayjs from "dayjs";
import { Locale } from "intlayer";
import { useIntlayer, useLocale } from "react-intlayer";

export default function InspectionsPage() {
  const { myInspections } = useUser();
  const navigate = useNavigate();
  const content = useIntlayer("inspections");
  const { locale } = useLocale();

  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  if (myInspections === undefined || myInspections.length < 1) {
    return (
      <Typography variant="h6" gutterBottom>
        {content.no_inspections}
      </Typography>
    );
  }

  const handleListItemClick = (id: string) => {
    console.log("Inspection id", id, "selected.");
    navigate({
      to: "/inspect/$inspectionId",
      params: { inspectionId: id },
      replace: true,
    });
  };

  return (
    <>
      <Typography variant="h6" gutterBottom>
        {content.my_inspections}
      </Typography>
      <List>
        {myInspections.map((inspection) => {
          const boat = useGetBoat(inspection.boatId);
          const boatname = boat?.name ?? "";
          const i9event = useGetI9Event(inspection.eventId);
          const day =
            i9event?.place +
            " " +
            dayjs(i9event?.day).toDate().toLocaleDateString(tlds);

          return (
            <ListItemButton
              key={inspection.inspectionId}
              disabled={inspection.completed !== null}
              onClick={(event) => handleListItemClick(inspection.inspectionId)}
            >
              <ListItemText primary={boatname} secondary={day} />
            </ListItemButton>
          );
        })}
      </List>
    </>
  );
}
