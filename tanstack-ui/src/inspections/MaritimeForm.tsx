import { formOptions } from "@tanstack/react-form";
import {
  InspectionProps,
  MaritimeData,
  useUpdateInspection,
} from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { Card } from "./form/Card";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";

export default function MaritimeForm({ data }: InspectionProps) {
  const maritime: MaritimeData = data.inspection.maritimeData;
  const equipmentOptions = formOptions({
    defaultValues: maritime,
  });
  const content = useIntlayer("maritime");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...equipmentOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.maritimeData = value;
      console.log("MaritimeData:", value);
      await updateInspection(data);
    },
  });

  return (
    <FormGrid justifyContent="flex-start">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit();
        }}
      >
        <Typography variant="h5" gutterBottom>
          5. {content.maritime}
        </Typography>

        <FormGroup
          sx={{
            position: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
          }}
        >
          <Stack direction="row" spacing={12}>
            <Stack direction="column">
              <form.AppField
                name="lights"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.lights.value} />
                )}
              />
              <form.AppField
                name="dayshapes"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.dayshapes.value} />
                )}
              />
              <form.AppField
                name="horn"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.horn.value} />
                )}
              />
              <form.AppField
                name="reflector"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reflector.value} />
                )}
              />
              <form.AppField
                name="compass"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.compass.value} />
                )}
              />
              <form.AppField
                name="bearing"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.bearing.value} />
                )}
              />
              <form.AppField
                name="log"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.log.value} />
                )}
              />
              <form.AppField
                name="charts"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.charts.value} />
                )}
              />
            </Stack>
            <Stack direction="column">
              <form.AppField
                name="radio"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.radio.value} />
                )}
              />
              <form.AppField
                name="satnav"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.satnav.value} />
                )}
              />
              <form.AppField
                name="radar"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.radar.value} />
                )}
              />
              <form.AppField
                name="spotlight"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.spotlight.value} />
                )}
              />
              <form.AppField
                name="vhf"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.vhf.value} />
                )}
              />
              <form.AppField
                name="hand_vhf"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.hand_vhf.value} />
                )}
              />
              <form.AppField
                name="documents"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.documents.value} />
                )}
              />
            </Stack>
          </Stack>
        </FormGroup>
        <Stack
          direction="row"
          justifyContent="right"
          spacing={4}
          sx={{ mt: 2 }}
        >
          <form.AppForm>
            <form.SubscribeButton label={content.submit.value} />
          </form.AppForm>
          <form.AppForm>
            <form.ResetButton label={content.reset.value} />
          </form.AppForm>
        </Stack>
      </form>
    </FormGrid>
  );
}
