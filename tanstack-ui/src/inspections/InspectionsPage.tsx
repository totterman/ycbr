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

  return (myInspections === undefined || myInspections.length < 1) ? (
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
          const placeday = inspection.place + ' ' + dayjs(inspection.day).toDate().toLocaleDateString(tlds);
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
              <ListItemText primary={inspection.boatName} secondary={placeday} />
            </ListItemButton>
          );
        })}
      </List>
    </>
  );
}
