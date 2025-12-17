import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import MenuItem from "@mui/material/MenuItem";

export default function NumberSelect({
  label,
  numbers,
}: {
  label: string;
  numbers: number[];
}) {
  const field = useFieldContext<string>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mt: 1 }}>
      <Typography sx={{ ml: 2 }}>{label}</Typography>
      <TextField
        select
        size="small"
        defaultValue={field.state.value}
        onBlur={field.handleBlur}
        onChange={(e) => field.handleChange(e.target.value)}
        sx={{  }}
        variant="outlined"
      >
        {numbers.map((nr) => (
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
