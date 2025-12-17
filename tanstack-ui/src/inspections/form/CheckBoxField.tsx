import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import { Checkbox, FormControlLabel } from "@mui/material";

export default function YcbrCheckBoxField({ label }: { label: string }) {
  const field = useFieldContext<boolean>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <div>
      <FormControlLabel
        control={
          <Checkbox
            size="small"
            checked={field.state.value}
            onChange={(e) => field.handleChange(e.target.checked)}
            onBlur={field.handleBlur}
          />
        }
        label={label}
        labelPlacement="start" 
        style={{ minWidth: 400, justifyContent: 'space-between' }}
      />
      {errors.map((error: string) => (
        <div key={error} style={{ color: "red" }}>
          {error}
        </div>
      ))}
    </div>
  );
}
