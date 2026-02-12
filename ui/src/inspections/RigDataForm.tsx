import { formOptions } from "@tanstack/react-form";
import { InspectionProps, RigData, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { useContext } from "react";
import { CategoryContext, isOfClass } from "./categorycontext";

export default function RigDataForm({ data }: InspectionProps) {
  const defaultRig: RigData = data.inspection.rigData;
  const rigOptions = formOptions({
    defaultValues: defaultRig,
  });
  const content = useIntlayer("rigdata");
  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...rigOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspectionClass = category.inspectionClass;
      data.inspection.rigData = value;
      console.log("RigData:", value);
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
          2. {content.rigTitle}
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
                name="rig"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="2.1" label={content.rig.value} />
                )}
              />
            )}
            {isOfClass(category, "1234") && (
              <form.AppField
                name="sails"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="2.2"
                    label={content.sails.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "1") && (
              <form.AppField
                name="storm_sails"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="2.3"
                    label={content.storm_sails.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && (
              <form.AppField
                name="storm_sails"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="2.3"
                    label={content.storm_sails.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "12") && (
              <form.AppField
                name="reefing"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="2.4"
                    label={content.reefing.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "3") && (
              <form.AppField
                name="reefing"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="2.4"
                    label={content.reefing.value}
                    rec={content.recommended.value}
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
