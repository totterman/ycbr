import { formOptions } from "@tanstack/react-form";
import { EngineData, InspectionProps, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { useContext } from "react";
import { CategoryContext, isMotorboat, isOfClass } from "./categorycontext";

export default function EngineDataForm({ data }: InspectionProps) {
  const defaultEngine: EngineData = data.inspection.engineData;
  const engineOptions = formOptions({
    defaultValues: defaultEngine,
  });
  const content = useIntlayer("enginedata");
  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...engineOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspectionClass = category.inspectionClass;
      data.inspection.engineData = value;
      console.log("Engine Data:", value);
      await updateInspection(data);
    },
  });

  return (
    <Stack>
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
          <Stack direction="column" sx={{ width: 1000 }}>
            {isOfClass(category, '1234') && (
              <form.AppField
                name="installation"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.1" label={content.installation.value} />
                )}
              />
            )}
            {isOfClass(category, '1234') && (
              <form.AppField
                name="controls"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.2" label={content.controls.value} />
                )}
              />
            )}
            {isOfClass(category, '1234') && (
              <form.AppField
                name="fuel_system"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.3" label={content.fuel_system.value} />
                )}
              />
            )}
            {isOfClass(category, '1234') && (
              <form.AppField
                name="cooling"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.4" label={content.cooling.value} />
                )}
              />
            )}
            {(isOfClass(category, '1') || (isOfClass(category, '2') && isMotorboat(category) )) && (
              <form.AppField
                name="strainer"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.5" label={content.strainer.value} />
                )}
              />
            )}
            {isOfClass(category, '3') && isMotorboat(category) && (
              <form.AppField
                name="strainer"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.5" label={content.strainer.value} rec={content.recommended.value} />
                )}
              />
            )}
            {isOfClass(category, '1234') && (
              <form.AppField
                name="electrical"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="3.6" label={content.electrical.value} />
                )}
              />
            )}

            {isOfClass(category, '12') && (
              <form.AppField
                name="separate_batteries"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.separate_batteries.value}
                  />
                )}
              />
            )}
            {isOfClass(category, '3') && (
              <form.AppField
                name="separate_batteries"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.separate_batteries.value} rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, '1234') && (
              <form.AppField
                name="shore_power"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.shore_power.value} />
                )}
              />
            )}

            {isOfClass(category, '1234') && (
              <form.AppField
                name="aggregate"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.aggregate.value} />
                )}
              />
            )}

            {isOfClass(category, '1') && (
              <form.AppField
                name="reserve"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reserve.value} />
                )}
              />
            )}

            {isOfClass(category, '2') && (
              <form.AppField
                name="reserve"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reserve.value} rec={content.recommended.value} />
                )}
              />
            )}

          </Stack>
        </FormGroup>
        <Stack
          direction="row"
          justifyContent="left"
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
    </Stack>
  );
}
