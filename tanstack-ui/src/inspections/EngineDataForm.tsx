import { formOptions } from "@tanstack/react-form";
import {
  EngineData,
  InspectionProps,
  RigData,
  useUpdateInspection,
} from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import Stack from "@mui/material/Stack";
import { Card } from "./form/Card";
import { FormGrid } from "./form/FormGrid";

export default function EngineDataForm({ data }: InspectionProps) {
  const defaultEngine: EngineData = data.inspection.engineData;
  const engineOptions = formOptions({
    defaultValues: defaultEngine,
  });

//  const { mutateAsync: updateInspection } = useUpdateInspection(data.id);

  const form = useAppForm({
    ...engineOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.engineData = value;
      console.log("Engine Data:", value);
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
              3. Engine
            </Typography>

              <FormGroup
                sx={{
                  position: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between",
                }}
              >
                <form.AppField
                  name="installation"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Engine Installation" />
                  )}
                />
                <form.AppField
                  name="controls"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Engine Controls" />
                  )}
                />
                <form.AppField
                  name="fuel_system"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Fuel system" />
                  )}
                />
                <form.AppField
                  name="cooling"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Cooling System" />
                  )}
                />
                <form.AppField
                  name="strainer"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Sea Water Strainers" />
                  )}
                />
                <form.AppField
                  name="separate_batteries"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Separate Start and Aux Batteries" />
                  )}
                />
                <form.AppField
                  name="shore_power"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Shore Power Installation" />
                  )}
                />
                <form.AppField
                  name="aggregate"
                  children={(field) => (
                    <field.MuiCheckBoxField label="Power Aggregate Installation" />
                  )}
                />
              </FormGroup>
          </form>
        </FormGrid>
      </Card>
    </>
  );
}
