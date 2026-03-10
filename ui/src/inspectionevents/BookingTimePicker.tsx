import { TimePickerProps, TimeView, TimePicker } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { Dispatch, SetStateAction, useState } from "react";
import { TimeSlot } from "./bookings";
import { useIntlayer } from "react-intlayer";

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
  const content = useIntlayer("i9events");

  const shouldDisableTime: TimePickerProps<Dayjs>["shouldDisableTime"] = (
    value: Dayjs,
    view: TimeView,
  ) => {
    // find available timeslots during current hour
    const freeSlots = timesBooked.filter(
      (x) =>
        x.time.year() === value.year() &&
        x.time.month() === value.month() &&
        x.time.date() === value.date() &&
        x.time.hour() === value.hour() &&
        x.available === true,
    );
    // disable current hour if no availability
    if (view === "hours" && freeSlots.length === 0) {
      return true;
    } else {
      // find current timeslot and disable if booked
      const thisSlot = timesBooked.find((x) => x.time.isSame(value));
      if (view === "minutes" && !!thisSlot) {
        return !thisSlot.available;
      }
    }
    return false;
  };

  const [value, setValue] = useState<dayjs.Dayjs | null>(null);
  return (
    <TimePicker
      label={content.select_time}
      value={value}
      onChange={(newValue) => {
        !!newValue && setTime(newValue);
      }}
      timeSteps={{ minutes: 20 }}
      minTime={mintime}
      maxTime={maxtime.subtract(20, "minutes")}
      referenceDate={dayjs(mintime)}
      shouldDisableTime={(value, view) => shouldDisableTime(value, view)}
      ampm={false}
    />
  );
}
