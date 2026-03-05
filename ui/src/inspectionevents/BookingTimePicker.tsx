import { TimePickerProps, TimeView, TimePicker } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { Dispatch, SetStateAction, useState } from "react";
import { TimeSlot } from "./bookings";

export function BookingTimePicker({
  mintime,
  maxtime,
  timesBooked,
  setTime,
}: {
  mintime: dayjs.Dayjs;
  maxtime: dayjs.Dayjs;
  timesBooked: TimeSlot[];
  setTime: Dispatch<SetStateAction<dayjs.Dayjs>>;
}) {
  const shouldDisableTime: TimePickerProps<Dayjs>["shouldDisableTime"] = (
    value: Dayjs,
    view: TimeView,
  ) => {
    const theSlot = timesBooked.find((x) => x.time.isSame(value));
    const hourSlots = timesBooked.filter(
      (x) =>
        x.time.year() === value.year() &&
        x.time.month() === value.month() &&
        x.time.date() === value.date() &&
        x.time.hour() === value.hour() &&
        x.available === false,
    );

    // if all times for this hour are booked, disable hour
    if (view === "hours" && hourSlots.length === 3) {
      console.log("HOURLY:", hourSlots);
      return true;
    } else {
      // disable exact time if booked
      if (view === "minutes" && !!theSlot) {
        return !theSlot.available;
      }
    }
    return false;
  };

  const [value, setValue] = useState<dayjs.Dayjs | null>(null);
  return (
    <TimePicker
      label="Välj besiktningstid"
      value={value}
      onChange={(newValue) => {
        setValue(newValue);
        !!newValue && setTime(newValue);
      }}
      timeSteps={{ minutes: 20 }}
      minTime={mintime}
      maxTime={maxtime.subtract(20, 'minutes')}
      referenceDate={dayjs(mintime)}
      shouldDisableTime={(value, view) => shouldDisableTime(value, view)}
      ampm={false}
    />
  );
}
