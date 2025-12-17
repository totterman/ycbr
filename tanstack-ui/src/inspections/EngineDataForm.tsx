import { formOptions } from "@tanstack/react-form";
import { EngineData, InspectionProps, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";

export default function EngineDataForm({ data }: InspectionProps) {
  const defaultEngine: EngineData = data.inspection.engineData;
  const engineOptions = formOptions({
    defaultValues: defaultEngine,
  });
  const content = useIntlayer("enginedata");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...engineOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.engineData = value;
      console.log("Engine Data:", value);
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
          3. {content.engine}
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
                name="installation"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.installation.value} />
                )}
              />
              <form.AppField
                name="controls"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.controls.value} />
                )}
              />
              <form.AppField
                name="fuel_system"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.fuel_system.value} />
                )}
              />
              <form.AppField
                name="cooling"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.cooling.value} />
                )}
              />
              <form.AppField
                name="strainer"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.strainer.value} />
                )}
              />
            </Stack>
            <Stack direction="column">
              <Typography variant="h6" sx={{ ml: 2 }} gutterBottom>
                {content.electrical}
              </Typography>
              <form.AppField
                name="separate_batteries"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.separate_batteries.value}
                  />
                )}
              />
              <form.AppField
                name="shore_power"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.shore_power.value} />
                )}
              />
              <form.AppField
                name="aggregate"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.aggregate.value} />
                )}
              />
            </Stack>
          </Stack>
        </FormGroup>
        <Stack direction="row" justifyContent="right" spacing={4} sx={{ mt: 2 }}>
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
