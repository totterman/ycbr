import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";

interface Props {
  label: string;
  ro?: boolean;
  options: { code: string, text: string }[],
}

// export default function TextSelect(props: Props) {
export default function TextSelect({ label, ro = false, options }: { label: string, ro?: boolean, options: { code: string, text: string }[] }) {
  const field = useFieldContext<string>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <div>
      <TextField
        select
        defaultValue={field.state.value}
        disabled={ro}
        label={label}
        onBlur={field.handleBlur}
        onChange={(e) => field.handleChange(e.target.value)}
        sx={{ mt: 0 }}
        variant="standard"
      >
        {options.map((opt) => (
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
