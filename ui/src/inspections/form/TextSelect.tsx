import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";

interface Props {
  label: string;
  options: { code: string, text: string }[],
}

export default function TextSelect(props: Props) {
  const field = useFieldContext<string>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <div>
      <TextField
        select
        defaultValue={field.state.value}
        label={props.label}
        onBlur={field.handleBlur}
        onChange={(e) => field.handleChange(e.target.value)}
        sx={{ mt: 0 }}
        variant="standard"
      >
        {props.options.map((opt) => (
          <MenuItem key={opt.code} value={opt.code}>
            {opt.text}
          </MenuItem>
        ))}
      </TextField>
      {errors.map((error: string) => (
        <div key={error} style={{ color: "red" }}>
          {error}
        </div>
      ))}
    </div>
  );
}
