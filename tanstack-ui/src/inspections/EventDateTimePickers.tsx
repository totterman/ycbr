import { MRT_Row } from "material-react-table";
import { InspectionDao } from "./inspection";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";

type RowProps = {
  row: MRT_Row<InspectionDao>;
  validationErrors: Record<string, string | undefined>;
};

const ISO_8601 = 'YYYY-MM-DDTHH:mm:ss.SSSZ';

export function DayDatePicker({ row, validationErrors }: RowProps) {
  return (
    <DatePicker
      label="Inspection Date"
      onAccept={(value) =>
        value ? (row._valuesCache["day"] = value.format(ISO_8601)) : undefined
      }
      disablePast
      slotProps={{
        textField: {
          error: !!validationErrors?.day,
          helperText: validationErrors.day,
          InputLabelProps: { shrink: true },
          margin: 'dense',
        },
      }}
    />
  );
}

export function FromTimePicker({ row, validationErrors }: RowProps) {
  return (
    <TimePicker
      label="Start Time"
      onAccept={(value) =>
        value ? (row._valuesCache["starts"] = value.format(ISO_8601)) : undefined
      }
      closeOnSelect={true}
      slotProps={{
        textField: {
          error: !!validationErrors?.starts,
          helperText: validationErrors.starts,
          InputLabelProps: { shrink: true },
        },
      }}
    />
  );
}

export function ToTimePicker({ row, validationErrors }: RowProps) {
  return (
    <TimePicker
      label="End Time"
      onAccept={(value) =>
        value ? (row._valuesCache["ends"] = value.format(ISO_8601)) : undefined
      }
      closeOnSelect={true}
      slotProps={{
        textField: {
          error: !!validationErrors?.ends,
          helperText: validationErrors.ends,
          InputLabelProps: { shrink: true },
        },
      }}
    />
  );
}
