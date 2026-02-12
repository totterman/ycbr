import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Stack,
  Tooltip,
} from "@mui/material";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import { InspectionDto, InspectionProps, Remark, useUpdateInspection } from "./inspection";
import { useIntlayer } from "react-intlayer";
import { useState } from "react";
import RemarkDialog from "./form/RemarkDialog";

export default function RemarkForm({ data, step }: { data: InspectionDto; step: number; }) {
  const content = useIntlayer("remarks");
  const remarks = data.remarks;

  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );
  const [remarkOpen, setRemarkOpen] = useState(false);

  const handleOpen = () => {
    setRemarkOpen(true);
  };

  const onClose = () => {
    setRemarkOpen(false);
  };

  async function handleCheckClick(remark: Remark) {
    data.remarks = remarks.filter((r) => r.id !== remark.id);
    await updateInspection(data);
  }

  return (
    <Stack direction="column" sx={{ width: 800 }}>
      <RemarkDialog open={remarkOpen} onClose={onClose} data={data} step={step} />

      <Card sx={{ border: 2, borderColor: "primary.main", borderRadius: 2 }}>
        <CardHeader title={!!remarks && remarks.length > 0 ? content.remarks.value : content.no_remarks.value} />
        <CardContent>
          {!!remarks && remarks.length > 0 && (
            <List>
              {remarks.map((remark: Remark) => (
                <ListItem>
                  <ListItemText primary={`${remark.item}  ${remark.text}`} />
                  <ListItemIcon>
                    <Tooltip title={content.check_remark.value}>
                      <CheckBoxIcon
                        color="success"
                        onClick={() => handleCheckClick(remark)}
                      />
                    </Tooltip>
                  </ListItemIcon>
                </ListItem>
              ))}
            </List>
          )}
        </CardContent>
        <CardActions>
          <Button variant="contained" onClick={handleOpen}>
            {content.new_remark}
          </Button>
        </CardActions>
      </Card>
    </Stack>
  );
}
