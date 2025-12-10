import { MRT_Row } from "material-react-table";
import { I9EventDto } from "./inspectionevent";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import { useIntlayer } from "react-intlayer";

type RowProps = {
  row: MRT_Row<I9EventDto>;
  validationErrors: Record<string, string | undefined>;
};

const ISO_8601 = 'YYYY-MM-DDTHH:mm:ssZ';

export function DayDatePicker({ row, validationErrors }: RowProps) {
  const content = useIntlayer("i9events");
  return (
    <DatePicker
      label={content.inspection_date.value}
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
  const content = useIntlayer("i9events");
  return (
    <TimePicker
      label={content.start_time.value}
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
  const content = useIntlayer("i9events");
  return (
    <TimePicker
      label={content.end_time.value}
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
