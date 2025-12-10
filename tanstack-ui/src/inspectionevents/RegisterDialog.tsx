import { MRT_Row } from "material-react-table";
import React from "react";
import { useUser } from "@/auth/useUser";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import EventIcon from "@mui/icons-material/Event";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import List from "@mui/material/List";
import ListItemText from "@mui/material/ListItemText";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";
import { I9EventDto, InspectorDto, useAddInspector } from "./inspectionevent";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";

type RowProps = {
  row: MRT_Row<I9EventDto>;
};

export default function RegisterDialog({ row }: RowProps) {
  // console.log("RegisterDialog", row.id);
  const [open, setOpen] = React.useState(false);
  const { user } = useUser();
  const content = useIntlayer("i9events");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const inspectionDay = new Date(row.original.day).toLocaleDateString(tlds);
  const timeslot =
    new Date(row.original.starts).toLocaleTimeString(locale.substring(0, 2)) +
    " - " +
    new Date(row.original.ends).toLocaleTimeString(locale.substring(0, 2));

  const { mutateAsync: addInspector } = useAddInspector();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleRegister = async () => {
    const id = row.original.id ?? -1;
    if (id === -1) {
      handleClose();
    }
    const dto: InspectorDto = {
      inspectorName: user.name,
      message: "Sent by " + user.name,
    };
    const props = { id: id, dto: dto };
    console.log("Inspector Registering: ", props);
    await addInspector(props);
    handleClose();
  };

  return (
    <React.Fragment>
      <Tooltip title={content.register_as.value}>
        <IconButton onClick={handleClickOpen}>
          <EventIcon color="secondary" />
        </IconButton>
      </Tooltip>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="register-dialog-title"
        aria-describedby="register-dialog-description"
      >
        <DialogTitle id="register-dialog-title">
          {content.register_title}
        </DialogTitle>
        <DialogContent>
          <List component="div" role="group">
            <ListItemText
              primary={content.inspector.value}
              secondary={user.name}
            />
            <ListItemText
              primary={content.location.value}
              secondary={row.original.place}
            />
            <ListItemText
              primary={content.date.value}
              secondary={inspectionDay}
            />
            <ListItemText primary={content.time.value} secondary={timeslot} />
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>{content.cancel}</Button>
          <Button onClick={handleRegister} autoFocus>
            {content.register}
          </Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
