import { formOptions } from "@tanstack/react-form";
import { HullData, InspectionProps, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import Stack from "@mui/material/Stack";
import { Card } from "./form/Card";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer, useLocale } from "react-intlayer";


export default function HullDataForm({ data }: InspectionProps) {

  const defaultHull: HullData = data.inspection.hullData;
  const hullOptions = formOptions({
    defaultValues: defaultHull,
  });
  const content = useIntlayer("hulldata");
  const { locale } = useLocale();
  

// const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...hullOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.hullData = value;
      console.log("HullData:", value);
//      await updateInspection(data);
    },
  });

  return (
    <>
      <Card variant="outlined">
        <FormGrid justifyContent="flex-start">
          <form
            onSubmit={(e) => {
              e.preventDefault();
              form.handleSubmit();
            }}
          >
            <Typography variant="h6" gutterBottom>
              1. {content.hullTitle}
            </Typography>

              <FormGroup sx={{position: 'flex', flexDirection: 'column', justifyContent: 'space-between'}}>
                <form.AppField
                  name="hull"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.hullCondition.value} />
                  )}
                />
                <form.AppField
                  name="openings"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.openings.value} />
                  )}
                />
                <form.AppField
                  name="material"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.material.value} />
                  )}
                />
                <form.AppField
                  name="keel_rudder"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.keel_rudder.value} />
                  )}
                />
                <form.AppField
                  name="steering"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.steering.value} />
                  )}
                />
                <form.AppField
                  name="drive_shaft_prop"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.drive_shaft_prop.value} />
                  )}
                />
                <form.AppField
                  name="throughulls"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.throughulls.value} />
                  )}
                />
                <form.AppField
                  name="fall_protection"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.fall_protection.value} />
                  )}
                />
                <form.AppField
                  name="heavy_objects"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.heavy_objects.value} />
                  )}
                />
                <form.AppField
                  name="fresh_water"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.fresh_water.value} />
                  )}
                />
                <form.AppField
                  name="lowest_leak"
                  children={(field) => (
                    <field.MuiTextField label={content.lowest_leak.value} help={content.leak_help.value} />
                  )}
                />
              </FormGroup>
          </form>
        </FormGrid>
</Card>
    </>
  );
}
