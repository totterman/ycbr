import { MRT_Row } from "material-react-table";
import React, { useMemo, useState } from "react";
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
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
} from "@mui/material";
import { BoatType } from "@/boats/boat";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";
import dayjs from "dayjs";
import { calculateSlots } from "./bookings";
import { BookingTimePicker } from "./BookingTimePicker";

type RowProps = {
  row: MRT_Row<I9EventDto>;
};

export default function BookingDialog({ row }: RowProps) {
  const ISO_8601 = "YYYY-MM-DDTHH:mm:ssZ";
  const [open, setOpen] = useState(false);
  const { user, myBoats } = useUser();
  const content = useIntlayer("i9events");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;

  const inspectionDay = new Date(row.original.day).toLocaleDateString(tlds);
  const minTime = dayjs(new Date(row.original.starts));
  const maxTime = dayjs(new Date(row.original.ends));
  const timeslot =
    new Date(row.original.starts).toLocaleTimeString(locale.substring(0, 2)) +
    " - " +
    new Date(row.original.ends).toLocaleTimeString(locale.substring(0, 2));
  const eventId = row.original.i9eventId;

  const [boat, setBoat] = useState<BoatType>();
  const [itype, setItype] = useState("A");
  const [itime, setItime] = useState(minTime);

  const { mutateAsync: createBooking } = useAddBoatBooking();

  const bookings = row.original.bookings.toSorted();
  const capacity = row.original.inspectors;

  const slots = useMemo(() => {
    return calculateSlots(minTime, maxTime, bookings, itype, capacity);
  }, [bookings, itype]);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const types = [
    { key: "A", text: content.annual },
    { key: "H", text: content.hull },
    { key: "B", text: content.base },
  ];

  const handleBook = async () => {
    const id = row.original.i9eventId ?? "";
    if (id === "") {
      handleClose();
    }
    if (boat === undefined) {
      handleClose();
    }
    console.log("My Boat:", boat);
    console.log("EventID:", eventId, "id", id);
    const dto: BoatBookingDto = {
      boatId: boat?.boatId ?? "",
      message: "Sent by " + user.name,
      type: itype,
      time: itime.format(ISO_8601),
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
            <Stack>
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
                      key={b.boatId}
                      value={b.name}
                      onClick={() => setBoat(b)}
                    >
                      {b.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <FormControl variant="standard" sx={{ m: 1, minWidth: 200 }}>
                <InputLabel id="booking-type-label">
                  {content.inspection_type}
                </InputLabel>
                <Select
                  labelId="booking-type-label"
                  id="booking-type"
                  label={content.inspection_type.value}
                >
                  {types.map((type) => (
                    <MenuItem
                      key={type.key}
                      value={type.key}
                      onClick={() => setItype(type.key)}
                    >
                      {type.text}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <BookingTimePicker
                mintime={minTime}
                maxtime={maxTime}
                timesBooked={slots}
                setTime={setItime}
              />

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
                <ListItemText
                  primary={content.time.value}
                  secondary={timeslot}
                />
              </List>
            </Stack>
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
