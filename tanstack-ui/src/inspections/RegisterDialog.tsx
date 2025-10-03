import { MRT_Row } from "material-react-table";
import { InspectionEventType } from "./inspection";
import React from "react";
import { useUser } from "@/auth/useUser";
import { useQueryClient } from "@tanstack/react-query";
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

type RowProps = {
  row: MRT_Row<InspectionEventType>;
};

export default function RegisterDialog({ row }: RowProps) {
  console.log("RegisterDialog", row.id);
  const [open, setOpen] = React.useState(false);
  const { user } = useUser();
  const timeslot = row.original.from + " - " + row.original.to;
  const queryClient = useQueryClient();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleRegister = () => {
    const uri = "/bff/api/i9event/" + row.id + "/inspector " + user.name;
    console.log("Call PUT", uri);
    queryClient.invalidateQueries({ queryKey: ["i9events"] });
    setOpen(false);
  };

  return (
    <React.Fragment>
      <Tooltip title="Register as Inspector">
        <IconButton onClick={handleClickOpen}>
          <EventIcon color="secondary"/>
        </IconButton>
      </Tooltip>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="register-dialog-title"
        aria-describedby="register-dialog-description"
      >
        <DialogTitle id="register-dialog-title">
          {"Register as Boat Inspector"}
        </DialogTitle>
        <DialogContent>
          <List component="div" role="group">
            <ListItemText primary="Inspector" secondary={user.name} />
            <ListItemText primary="Location" secondary={row.original.place} />
            <ListItemText primary="Date" secondary={row.original.day} />
            <ListItemText primary="Time" secondary={timeslot} />
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleRegister} autoFocus>
            Register
          </Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
