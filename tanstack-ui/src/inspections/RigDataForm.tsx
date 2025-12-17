import { formOptions } from "@tanstack/react-form";
import { InspectionProps, RigData, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";

export default function RigDataForm({ data }: InspectionProps) {
  const defaultRig: RigData = data.inspection.rigData;
  const rigOptions = formOptions({
    defaultValues: defaultRig,
  });
  const content = useIntlayer("rigdata");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...rigOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.rigData = value;
      console.log("RigData:", value);
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
          2. {content.rigTitle}
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
                name="rig"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.rig.value} />
                )}
              />
              <form.AppField
                name="sails"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.sails.value} />
                )}
              />
            </Stack>
            <Stack direction="column">
              <form.AppField
                name="storm_sails"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.storm_sails.value} />
                )}
              />
              <form.AppField
                name="reefing"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reefing.value} />
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
