import { useUser } from "@/auth/useUser";
import {
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Tooltip,
} from "@mui/material";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import { useNavigate } from "@tanstack/react-router";
import dayjs from "dayjs";
import { Locale } from "intlayer";
import { useIntlayer, useLocale } from "react-intlayer";
import SailingIcon from "@mui/icons-material/Sailing";
import CancelIcon from "@mui/icons-material/Cancel";
import { allInspectionsQueryOptions, inspectionsQueryOptions, MyInspectionsDto, useDeleteInspection } from "./inspection";
import {
  BoatBookingDto,
  useMarkBoatBooking,
} from "@/inspectionevents/inspectionevent";
import { useSuspenseQuery } from "@tanstack/react-query";

export default function InspectionsPage() {
  const { user, myInspections } = useUser();
  const navigate = useNavigate();
  const { mutateAsync: markBooking } = useMarkBoatBooking();
  const { mutateAsync: deleteInspection } = useDeleteInspection();
  const content = useIntlayer("inspections");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const inspections = user.isInspector ? myInspections : useSuspenseQuery(allInspectionsQueryOptions).data;

  const handleListItemClick = (id: string) => {
    console.log("Inspection id", id, "selected.");
    navigate({
      to: "/inspect/$inspectionId",
      params: { inspectionId: id },
      replace: true,
    });
  };

  const handleReleaseClick = async (inspection: MyInspectionsDto) => {
    console.log("Inspection:", inspection);

    const dto: BoatBookingDto = {
      boatId: inspection.boatId,
      message: "Sent by " + inspection.inspectorName,
      taken: false,
    };

    await markBooking({ id: inspection.eventId, dto: dto });
    await deleteInspection(inspection.inspectionId);
  };

  return inspections === undefined || inspections.length < 1 ? (
    <Typography variant="h6" gutterBottom>
      {content.no_inspections}
    </Typography>
  ) : (
    <>
      <Typography variant="h6" gutterBottom>
        {user.isStaff ? content.inspections : content.my_inspections}
      </Typography>
      <List>
        {inspections.map((inspection) => {
          const placeday =
            inspection.place +
            " " +
            dayjs(inspection.day).toDate().toLocaleDateString(tlds);
          return (
            <ListItem>
              <ListItemIcon>
                {inspection.completed !== null && (
                  <SailingIcon color="success" />
                )}
                {inspection.completed === null && (
                  <SailingIcon color="primary" />
                )}
              </ListItemIcon>
              <ListItemButton
                key={inspection.inspectionId}
                disabled={inspection.completed !== null}
                onClick={(event) =>
                  handleListItemClick(inspection.inspectionId)
                }
              >
                <ListItemText
                  primary={inspection.boatName}
                  secondary={placeday}
                />
                <ListItemText primary={inspection.inspectionClass} />
              </ListItemButton>
              <ListItemIcon>
                {inspection.completed === null && (
                  <Tooltip title={content.release.value}>
                  <CancelIcon
                    color="warning"
                    onClick={() => handleReleaseClick(inspection)}
                  />
                  </Tooltip>
                )}
              </ListItemIcon>
            </ListItem>
          );
        })}
      </List>
    </>
  );
}
