import { createFileRoute } from "@tanstack/react-router";
import { Typography } from "@mui/material";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { useUser } from "@/auth/useUser";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"
import { DateField } from "@mui/x-date-pickers";
import { useState } from "react";
import dayjs from "dayjs";

export const Route = createFileRoute("/dispatch")({
  component: DispatchPage,
});

function DispatchPage() {
  const [inspectionDate, setInspectionDate] = useState<dayjs.Dayjs | null>()

  return (
    <>
      <Typography variant="body1">Boat inspection dispatch page</Typography>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="sv">
        <DatePicker onAccept = {(value) => setInspectionDate(value ?? null)}  />
      </LocalizationProvider>
      {inspectionDate &&
      <Typography variant="body1"> New inspection on { inspectionDate?.format('D.M.YYYY') }</Typography>
      }
    </>
  );
}
