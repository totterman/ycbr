import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Select from "@mui/material/Select";
import { useState } from "react";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import { InspectionDto, Remark, useUpdateInspection } from "../inspection";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";

export default function RemarkDialog({
  open,
  onClose,
  data,
  step
}: {
  open: boolean;
  onClose: () => void;
  data: InspectionDto;
  step: number;
}) {
  const content = useIntlayer("remarks");

  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );

  const remarks = data.remarks;

  function findMaxIndex() {
    if (!remarks || (remarks && remarks.length === 0)) return 0;
    return remarks.reduce((acc, rem) => {
      return rem.id > acc ? rem.id + 1 : acc + 1;
    }, 0);
  }

  async function handleSubmit(event: React.SubmitEvent<HTMLFormElement>) {
    event.preventDefault();
    event.stopPropagation();
    const nextId = findMaxIndex();
    const formData = new FormData(event.currentTarget);
    const formJson = Object.fromEntries((formData as any).entries());
    const newRemark: Remark = { id: nextId, item: item, text: formJson.text };
    data.remarks = [...remarks, newRemark];
    await updateInspection(data);

    onClose();
  }

  const [item, setItem] = useState("");
  const items = [
    ... (step === 0) ? ["1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "1.10", "1.11"] : [],
    ... (step === 1) ? ["2.1", "2.2", "2.3", "2.4"] : [],
    ... (step === 2) ? ["3.1", "3.2", "3.3", "3.4", "3.5", "3.6"] : [],
    ... (step === 3) ? ["4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9", "4.10", "4.11", "4.12", "4.13", "4.14", "4.15"] : [],
    ... (step === 4) ? ["5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10", "5.11", "5.12", "5.13", "5.14"] : [],
    ... (step === 5) ? ["6.1", "6.2", "6.3", "6.4", "6.5", "6.6", "6.7", "6.8", "6.9", "6.10", "6.11", "6.12", "6.13"] : [],
];

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{content.new_remark}</DialogTitle>
      <DialogContent>
        <DialogContentText>{content.enter_remark}</DialogContentText>
        <form onSubmit={handleSubmit} id="subscription-form">
          <Stack direction={"row"}>
            <FormControl
              variant="standard"
              required
              sx={{ m: 1, minWidth: 60 }}
            >
              <InputLabel id="demo-simple-select-label">
                {content.remark_item}
              </InputLabel>
              <Select label="INSPECTION_ITEM" autoWidth autoFocus value={item}>
                {items.map((item) => (
                  <MenuItem value={item} onClick={() => setItem(item)}>
                    {item}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              required
              multiline
              fullWidth
              margin="dense"
              id="text"
              name="text"
              label={content.remark_text.value}
              type="text"
              variant="standard"
              sx={{ width: 300 }}
              slotProps={{ htmlInput: { maxLength: 255 }  }}
            />
          </Stack>
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>{content.cancel}</Button>
        <Button type="submit" form="subscription-form">
          {content.save}
        </Button>
      </DialogActions>
    </Dialog>
  );
}
