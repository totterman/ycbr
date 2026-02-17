import { TimePickerProps, TimeView, TimePicker } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { Dispatch, SetStateAction, useState } from "react";

export function BookingTimePicker({
  mintime,
  maxtime,
  type,
  timesBooked,
  setTime,
}: {
  mintime: dayjs.Dayjs;
  maxtime: dayjs.Dayjs;
  type: string;
  timesBooked: string[];
  setTime: Dispatch<SetStateAction<dayjs.Dayjs>>;
}) {
  const ISO_8601 = "YYYY-MM-DDTHH:mm:ssZ";
  const disabledTimes = timesBooked.map((dateTime) => dayjs(dateTime, ISO_8601));

  function isSameDate(left: dayjs.Dayjs, right: dayjs.Dayjs): boolean {
    return (
      left.year() === right.year() &&
      left.month() === right.month() &&
      left.date() === right.date()
    );
  }

  function isSameTime(left: dayjs.Dayjs, right: dayjs.Dayjs): boolean {
    return (
      left.hour() === right.hour() &&
      left.minute() === right.minute()
    );
  }

  function isOneSlotBefore(left: dayjs.Dayjs, right: dayjs.Dayjs, minutesStepValue: number): boolean {
    return (
        (left.minute() === 0
          ? left.hour() - 1 === right.hour() && (60 - minutesStepValue) === right.minute()
          : left.hour() === right.hour() && left.minute() - minutesStepValue === right.minute())
    );
  }

  function isTwoSlotsBefore(left: dayjs.Dayjs, right: dayjs.Dayjs, minutesStepValue: number): boolean {
    return (
        (left.minute() === 0
          ? left.hour() - 1 === right.hour() && (60 - (2 * minutesStepValue)) === right.minute()
          : left.minute() === minutesStepValue
          ? left.hour() - 1 === right.hour() && (60 - minutesStepValue) === right.minute()
          : left.hour() === right.hour() && left.minute() - (2 * minutesStepValue) === right.minute())
    );
  }

  function isThreeSlotsBefore(left: dayjs.Dayjs, right: dayjs.Dayjs, minutesStepValue: number): boolean {
    return (
        (left.minute() === 0
          ? left.hour() - 1 === right.hour() && (60 - (3 * minutesStepValue)) === right.minute()
          : left.minute() === minutesStepValue
          ? left.hour() - 1 === right.hour() && (60 - (2 * minutesStepValue)) === right.minute()
          : left.minute() === (2 * minutesStepValue)
          ? left.hour() - 1 === right.hour() && (60 - minutesStepValue) === right.minute()
          : left.hour() === right.hour() && left.minute() - (3 * minutesStepValue) === right.minute())
    );
  }

  const shouldDisableTime: TimePickerProps<Dayjs>["shouldDisableTime"] = (
    value: Dayjs,
    view: TimeView,
    minutesStepValue: number = 10,
  ) => {
    let shouldDisable = false;

    for (let i = 0; i < disabledTimes.length; i++) {
      const disabled = disabledTimes[i];
      const disLength = disabledTimes.filter(
        (x) => x.year() === value.year() && x.month() === value.month() && x.date() === value.date() && x.hour() === value.hour(),
      ).length;

      if (
        minutesStepValue > 1 &&
        view === "hours" &&
        isSameDate(disabled, value) &&
        disabled.hour() === value.hour() &&
        (disLength === 60 / minutesStepValue ||
          disLength % (60 / minutesStepValue) === 0)
      ) {
        console.log("DEBUG value:    ", value.date(), "value.hour:   ", value.hour(), "value.minute:   ", value.minute());
        console.log("DEBUG disabled: ", disabled.date(), "disabled.hour", disabled.hour(), "disabled.minute:", disabled.minute());
        shouldDisable = true;
        break;
      } else if (
        view === "minutes" &&
        isSameDate(disabled, value) &&
        isSameTime(disabled, value)
      ) {
        shouldDisable = true;
        break;
      } else if (
        'YHB'.includes(type) &&
        view === "minutes" &&
        isSameDate(disabled, value) &&
        isOneSlotBefore(disabled, value, minutesStepValue)
      ) {
        shouldDisable = true;
        break;
      } else if (
        'HB'.includes(type) &&
        view === "minutes" &&
        isSameDate(disabled, value) &&
        isTwoSlotsBefore(disabled, value, minutesStepValue)
      ) {
        shouldDisable = true;
        break;
      } else if (
        'B'.includes(type) &&
        view === "minutes" &&
        isSameDate(disabled, value) &&
        isThreeSlotsBefore(disabled, value, minutesStepValue)
      ) {
        shouldDisable = true;
        break;
      }
    }
    return shouldDisable;
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
      timeSteps={{ minutes: 10 }}
      minTime={mintime}
      maxTime={maxtime}
      referenceDate={dayjs(mintime)}
      shouldDisableTime={(value, view) => shouldDisableTime(value, view)}
      ampm={false}
    />
  );
}
