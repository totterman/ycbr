import { MRT_Row } from "material-react-table";
import React from "react";
import { useUser } from "@/auth/useUser";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import SailingIcon from "@mui/icons-material/SailingSharp";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import List from "@mui/material/List";
import ListItemText from "@mui/material/ListItemText";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";
import { BoatBookingDao, InspectionDao, useAddBoatBooking } from "./inspection";
import { TextField } from "@mui/material";

type RowProps = {
  row: MRT_Row<InspectionDao>;
};

export default function BookingDialog({ row }: RowProps) {
  const [open, setOpen] = React.useState(false);
  const { user } = useUser();
  const timeslot = row.original.starts + " - " + row.original.ends;

  const {
    mutateAsync: createBooking,
  } = useAddBoatBooking();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const id = row.original.id ?? -1;
    if (id === -1) {
      handleClose();
    }
    const formData = new FormData(event.currentTarget);
    const formJson = Object.fromEntries((formData as any).entries());
    const boatname = formJson.boatname;
    const dao: BoatBookingDao = {
      boatName: boatname,
      message: "Sent by " + user.name,
    };
    const props = { id: id, dao: dao };
    console.log("BoatBooking: ", props);
    await createBooking(props);
    handleClose();
  };

  return (
    <React.Fragment>
      <Tooltip title="Book Inspection">
        <IconButton onClick={handleClickOpen}>
          <SailingIcon color="primary" />
        </IconButton>
      </Tooltip>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="booking-dialog-title"
        aria-describedby="booking-dialog-description"
      >
        <DialogTitle id="booking-dialog-title">
          {"Book Boat Inspection"}
        </DialogTitle>
        <DialogContent>
          <form onSubmit={handleSubmit} id="booking-form">
            <TextField
              autoFocus
              required
              margin="dense"
              id="boat"
              name="boatname"
              label="Boat name"
              type="text"
              fullWidth
              variant="standard"
            />
          </form>
          <List component="div" role="group">
            <ListItemText primary="Boat Owner" secondary={user.name} />
            <ListItemText primary="Location" secondary={row.original.place} />
            <ListItemText primary="Date" secondary={row.original.day} />
            <ListItemText primary="Time" secondary={timeslot} />
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit" form="booking-form" autoFocus>
            Book
          </Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
