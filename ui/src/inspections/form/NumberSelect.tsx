import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import MenuItem from "@mui/material/MenuItem";

interface Props {
  nr?: string;
  label: string;
  numbers: number[];
}

export default function NumberSelect(props: Props) {
  const field = useFieldContext<string>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <Stack direction="row" alignItems="center" justifyContent="flex-start" sx={{ mt: 1 }}>
      <Typography sx={{ width: 20 }} variant="body2">
        {props.nr}
      </Typography>

      <Typography sx={{ width: 600 }}>{props.label}</Typography>
      <TextField
        select
        size="small"
        defaultValue={field.state.value}
        onBlur={field.handleBlur}
        onChange={(e) => field.handleChange(e.target.value)}
        sx={{  }}
        variant="outlined"
      >
        {props.numbers.map((nr) => (
          <MenuItem key={nr} value={nr}>
            {nr}
          </MenuItem>
        ))}
      </TextField>
      {errors.map((error: string) => (
        <div key={error} style={{ color: "red" }}>
          {error}
        </div>
      ))}
    </Stack>
  );
}
