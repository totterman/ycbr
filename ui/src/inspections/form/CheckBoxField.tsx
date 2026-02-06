import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import { Checkbox, FormControlLabel } from "@mui/material";
import { Typography } from "@mui/material";
import Stack from "@mui/material/Stack";

interface Props {
  nr?: string;
  label: string;
  rec?: string;
}
export default function YcbrCheckBoxField(props: Props) {
  const field = useFieldContext<boolean>();

  const errors = useStore(field.store, (state) => state.meta.errors);

  return (
    <Stack direction="row" spacing={4} alignItems="center">
      <Typography sx={{ width: 20 }} variant="body1">
        {props.nr}
      </Typography>
      <FormControlLabel
        control={
          <Checkbox
            size="small"
            checked={field.state.value}
            onChange={(e) => field.handleChange(e.target.checked)}
            onBlur={field.handleBlur}
          />
        }
        label={props.label}
        labelPlacement="start" 
        style={{ minWidth: 600, justifyContent: 'space-between' }}
      />
      {errors.map((error: string) => (
        <div key={error} style={{ color: "red" }}>
          {error}
        </div>
      ))}
      <Typography variant="body2">
        {props.rec}
      </Typography>
    </Stack>
  );
}
