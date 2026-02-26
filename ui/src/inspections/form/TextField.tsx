import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import TextField from "@mui/material/TextField";

export default function YcbrTextField({ label, ro = false, help, placeholder }: { label: string, ro?: boolean, help?: string, placeholder?: string }) {
  const field = useFieldContext<string>();
  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <div>
        <TextField
          defaultValue={field.state.value}
          disabled={ro}
          fullWidth
          helperText={help}
          label={label}
          onBlur={field.handleBlur}
          onChange={(e) => field.handleChange(e.target.value)}
          placeholder={placeholder}
          sx={{ mt: 0 }}
          variant="standard"
        />
      {errors.map((error: string) => (
        <div key={error} style={{ color: "red" }}>
          {error}
        </div>
      ))}
    </div>
  );
}
