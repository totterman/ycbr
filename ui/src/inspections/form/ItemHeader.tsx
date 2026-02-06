import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

interface Props {
  nr?: string;
  label: string;
}
export default function ItemHeader(props: Props) {
  return (
    <Stack direction="row" spacing={4} alignItems="center" sx={{ mt: 2 }}>
      <Typography sx={{ width: 20 }} variant="body1">
        {props.nr}
      </Typography>
      <Typography variant="body1">
        {props.label}
      </Typography>
    </Stack>
  );
}
