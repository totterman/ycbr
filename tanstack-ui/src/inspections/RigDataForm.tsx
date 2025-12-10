import { formOptions } from "@tanstack/react-form";
import { InspectionProps, RigData, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import Stack from "@mui/material/Stack";
import { Card } from "./form/Card";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer, useLocale } from "react-intlayer";


export default function RigDataForm({ data }: InspectionProps) {

  const defaultRig: RigData = data.inspection.rigData;
  const rigOptions = formOptions({
    defaultValues: defaultRig,
  });
  const content = useIntlayer("rigdata");
  const { locale } = useLocale();


// const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...rigOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.rigData = value;
      console.log("RigData:", value);
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
              2. {content.rigTitle}
            </Typography>

              <FormGroup sx={{position: 'flex', flexDirection: 'column', justifyContent: 'space-between'}}>
                <form.AppField
                  name="rig"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.rig.value} />
                  )}
                />
                <form.AppField
                  name="sails"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.sails.value} />
                  )}
                />
                <form.AppField
                  name="storm_sails"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.storm_sails.value} />
                  )}
                />
                <form.AppField
                  name="reefing"
                  children={(field) => (
                    <field.MuiCheckBoxField label={content.reefing.value} />
                  )}
                />
              </FormGroup>
{/*
              <Stack direction={"row"} spacing={4}>
                <form.AppForm>
                  <form.SubscribeButton label="Submit" />
                </form.AppForm>
                <form.AppForm>
                  <form.ResetButton label="Reset" />
                </form.AppForm>
              </Stack>
*/}
          </form>
        </FormGrid>
</Card>
    </>
  );
}
