import { MRT_Row } from "material-react-table";
import React, { useState } from "react";
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
import {
  BoatBookingDto,
  I9EventDto,
  useAddBoatBooking,
} from "./inspectionevent";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { BoatType } from "@/boats/boat";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";

type RowProps = {
  row: MRT_Row<I9EventDto>;
};

export default function BookingDialog({ row }: RowProps) {
  const [open, setOpen] = useState(false);
  const { user, myBoats } = useUser();
  const content = useIntlayer("i9events");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const inspectionDay = new Date(row.original.day).toLocaleDateString(tlds);
  const timeslot =
    new Date(row.original.starts).toLocaleTimeString(locale.substring(0, 2)) +
    " - " +
    new Date(row.original.ends).toLocaleTimeString(locale.substring(0, 2));
  const [boat, setBoat] = useState<BoatType>();

  const { mutateAsync: createBooking } = useAddBoatBooking();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleBook = async () => {
    const id = row.original.id ?? -1;
    if (id === -1) {
      handleClose();
    }
    if (boat === undefined) {
      handleClose();
    }
    console.log("My Boat:", boat);
    const dto: BoatBookingDto = {
      boatId: boat?.id ?? -1,
      message: "Sent by " + user.name,
      taken: false,
    };
    const props = { id: id, dto: dto };
    console.log("BoatBooking: ", props);
    await createBooking(props);
    handleClose();
  };

  return (
    !!myBoats && (
      <React.Fragment>
        <Tooltip title={content.book_inspection.value}>
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
            {content.book_inspection_title}
          </DialogTitle>
          <DialogContent>
            <FormControl variant="standard" sx={{ m: 1, minWidth: 200 }}>
              <InputLabel id="booking-boatname-label">
                {content.boat_name}
              </InputLabel>
              <Select
                labelId="booking-boatname-label"
                id="booking-boatname"
                label={content.boat_name.value}
              >
                {myBoats.map((b) => (
                  <MenuItem
                    key={b.id}
                    value={b.name}
                    onClick={() => setBoat(b)}
                  >
                    {b.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <List component="div" role="group">
              <ListItemText
                primary={content.boat_owner.value}
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
            <Button onClick={handleBook}>{content.book}</Button>
          </DialogActions>
        </Dialog>
      </React.Fragment>
    )
  );
}
