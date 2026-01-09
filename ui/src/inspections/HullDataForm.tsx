import { formOptions } from "@tanstack/react-form";
import { HullData, InspectionProps, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import Stack from "@mui/material/Stack";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";

export default function HullDataForm({ data }: InspectionProps) {
  const defaultHull: HullData = data.inspection.hullData;
  const hullOptions = formOptions({
    defaultValues: defaultHull,
  });
  const content = useIntlayer("hulldata");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.inspectionId);

  const form = useAppForm({
    ...hullOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.hullData = value;
      console.log("HullData:", value);
      await updateInspection(data);
    },
  });

  const leaks = [0, 10, 20, 30, 40, 50];

  return (
    <FormGrid justifyContent="flex-start">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit();
        }}
      >
        <Typography variant="h5" gutterBottom>
          1. {content.hullTitle}
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
                name="hull"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.hullCondition.value} />
                )}
              />
              <form.AppField
                name="openings"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.openings.value} />
                )}
              />
              <form.AppField
                name="material"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.material.value} />
                )}
              />
              <form.AppField
                name="keel_rudder"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.keel_rudder.value} />
                )}
              />
              <form.AppField
                name="steering"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.steering.value} />
                )}
              />
              <form.AppField
                name="drive_shaft_prop"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.drive_shaft_prop.value}
                  />
                )}
              />
            </Stack>

            <Stack direction="column">
              <form.AppField
                name="throughulls"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.throughulls.value} />
                )}
              />
              <form.AppField
                name="fall_protection"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.fall_protection.value}
                  />
                )}
              />
              <form.AppField
                name="heavy_objects"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.heavy_objects.value} />
                )}
              />
              <form.AppField
                name="fresh_water"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.fresh_water.value} />
                )}
              />
              <form.AppField
                name="lowest_leak"
                children={(field) => (
                  <field.NumberSelect
                    label={content.lowest_leak.value}
                    numbers={leaks}
                  />
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
