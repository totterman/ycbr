import { formOptions } from "@tanstack/react-form";
import { HullData, InspectionProps, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import Stack from "@mui/material/Stack";
import { useIntlayer } from "react-intlayer";
import { useContext, useState } from "react";
import { CategoryContext, isOfClass } from "./categorycontext";

export default function HullDataForm({ data }: InspectionProps) {
  const defaultHull: HullData = data.inspection.hullData;
  const hullOptions = formOptions({
    defaultValues: defaultHull,
  });
  const content = useIntlayer("hulldata");
  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...hullOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspectionClass = category.inspectionClass;
      data.inspection.hullData = value;
      console.log(
        "HullData:",
        value,
        "Boat Kind:",
        category.kind,
        "Inspection Class:",
        category.inspectionClass,
      );
      await updateInspection(data);
    },
  });

  const leaks = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100];

  return (
    <Stack>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit();
        }}
      >
        <Typography variant="h5" gutterBottom>
          {content.hullTitle}
        </Typography>

        <FormGroup
          sx={{
            position: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
          }}
        >
          <Stack direction="column" sx={{ width: 1000 }}>
            {isOfClass(category, "1234") && (
              <form.AppField
                name="hull"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.1"
                    label={content.hullCondition.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "123") && (
              <form.AppField
                name="openings"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.2"
                    label={content.openings.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="material"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.3"
                    label={content.material.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="keel_rudder"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.4"
                    label={content.keel_rudder.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="steering"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.5"
                    label={content.steering.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="drive_shaft_prop"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.6"
                    label={content.drive_shaft_prop.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "123") && (
              <form.AppField
                name="throughulls"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.7"
                    label={content.throughulls}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="throughulls"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.7"
                    label={content.throughulls}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1234") && (
              <form.AppField
                name="fall_protection"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.8"
                    label={content.fall_protection.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="heavy_objects"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.9"
                    label={content.heavy_objects.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "123") && (
              <form.AppField
                name="fresh_water"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.10"
                    label={content.fresh_water.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="fresh_water"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="1.10"
                    label={content.fresh_water.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1234") && (
              <form.AppField
                name="lowest_leak"
                children={(field) => (
                  <field.NumberSelect
                    label={content.lowest_leak.value}
                    numbers={leaks}
                  />
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
