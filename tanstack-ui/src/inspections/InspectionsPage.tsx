import { useUser } from "@/auth/useUser";
import { useGetBoat } from "@/boats/boat";
import { useGetI9Event } from "@/inspectionevents/inspectionevent";
import { ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import { useNavigate } from "@tanstack/react-router";
import dayjs from "dayjs";
import { Locale } from "intlayer";
import { useIntlayer, useLocale } from "react-intlayer";
import SailingIcon from "@mui/icons-material/Sailing";

export default function InspectionsPage() {
  const { myInspections } = useUser();
  const navigate = useNavigate();
  const content = useIntlayer("inspections");
  const { locale } = useLocale();

  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const handleListItemClick = (id: string) => {
    console.log("Inspection id", id, "selected.");
    navigate({
      to: "/inspect/$inspectionId",
      params: { inspectionId: id },
      replace: true,
    });
  };
  const NO_INSPECTIONS = (myInspections === undefined || myInspections.length < 1);
  const worklist: Map<string, { boatname: string; day: string }> = new Map();

  if (!NO_INSPECTIONS) {
    myInspections.map((inspection) => {
      const boat = useGetBoat(inspection.boatId);
      const thisboatname = boat?.name ?? "";
      const i9event = useGetI9Event(inspection.eventId);
      const thisday =
        i9event?.place +
        " " +
        dayjs(i9event?.day).toDate().toLocaleDateString(tlds);
      worklist.set(inspection.inspectionId, {
        boatname: thisboatname,
        day: thisday,
      });
    });
  }
  console.log("worklist:", worklist);

  return (NO_INSPECTIONS) ? (
    <Typography variant="h6" gutterBottom>
      {content.no_inspections}
    </Typography>
  ) : (
    <>
      <Typography variant="h6" gutterBottom>
        {content.my_inspections}
      </Typography>
      <List>
        {myInspections.map((inspection) => {
          const boatname = worklist.get(inspection.inspectionId)?.boatname;
          const day = worklist.get(inspection.inspectionId)?.day;
          return (
            <ListItemButton
              key={inspection.inspectionId}
              disabled={inspection.completed !== null}
              onClick={(event) => handleListItemClick(inspection.inspectionId)}
            >
              <ListItemIcon>
                {inspection.completed !== null && (
                  <SailingIcon color="success" />
                )}
                {inspection.completed == null && (
                  <SailingIcon color="primary" />
                )}
              </ListItemIcon>
              <ListItemText primary={boatname} secondary={day} />
            </ListItemButton>
          );
        })}
      </List>
    </>
  );
}
