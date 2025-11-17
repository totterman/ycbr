import { createFileRoute } from "@tanstack/react-router";
import { Typography } from "@mui/material";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"
import { TimePicker } from "@mui/x-date-pickers";
import { useState } from "react";
import 'dayjs/locale/sv';
import 'dayjs/locale/fi';
import 'dayjs/locale/en';
import { Dayjs } from "dayjs";

export const Route = createFileRoute("/dispatch")({
  component: DispatchPage,
});

function DispatchPage() {
  const [i9eventDate, seti9eventDate] = useState<Dayjs | null>();
  const [fromTime, setFromTime] = useState<Dayjs | null>();
  const [toTime, setToTime] = useState<Dayjs | null>();

  return (
    <>
      <Typography variant="body1">Boat inspection dispatch page</Typography>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="sv">
        <DatePicker label="Inspection Date" onAccept = {(value) => seti9eventDate(value ?? null)}  />
        <TimePicker label="Start Time" onAccept = {(value) => setFromTime(value ?? null)}  />
        <TimePicker label="End Time" onAccept = {(value) => setToTime(value ?? null)}  />
      </LocalizationProvider>
      {i9eventDate && <Typography variant="body1">New inspection on { i9eventDate?.format('D.M.YYYY') }</Typography> }
      {fromTime && <Typography variant="body1">Starts at { fromTime?.format('HH.mm') }</Typography> }
      {toTime && <Typography variant="body1">Ends at { toTime?.format('HH.mm') }</Typography> }
    </>
  );
}
