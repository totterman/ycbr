import { formOptions } from "@tanstack/react-form";
import {
  EquipmentData,
  InspectionProps,
  useUpdateInspection,
} from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";

export default function EquipmentForm({ data }: InspectionProps) {
  const equipment: EquipmentData = data.inspection.equipmentData;
  const equipmentOptions = formOptions({
    defaultValues: equipment,
  });
  const content = useIntlayer("equipment");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.inspectionId);

  const form = useAppForm({
    ...equipmentOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.equipmentData = value;
      console.log("EquipmentData:", value);
      await updateInspection(data);
    },
  });

  const numbers = [0, 1, 2, 4];

  return (
    <FormGrid justifyContent="flex-start">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit();
        }}
      >
        <Typography variant="h5" gutterBottom>
          4. {content.equipment}
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
                name="markings"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.markings.value} />
                )}
              />
              <form.AppField
                name="anchors"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.anchors.value} />
                )}
              />
              <form.AppField
                name="sea_anchor"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.sea_anchor.value} />
                )}
              />
              <form.AppField
                name="lines"
                children={(field) => (
                  <field.NumberSelect
                    label={content.lines.value}
                    numbers={numbers}
                  />
                )}
              />
              <form.AppField
                name="tools"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.tools.value} />
                )}
              />
              <form.AppField
                name="paddel"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.paddel.value} />
                )}
              />
              <form.AppField
                name="hook"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.hook.value} />
                )}
              />
              <form.AppField
                name="resque_line"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.resque_line.value} />
                )}
              />
            </Stack>
            <Stack direction="column">
              <form.AppField
                name="fenders"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.fenders.value} />
                )}
              />
              <form.AppField
                name="ladders"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.ladders.value} />
                )}
              />
              <form.AppField
                name="defroster"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.defroster.value} />
                )}
              />
              <form.AppField
                name="toilet"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.toilet.value} />
                )}
              />
              <form.AppField
                name="gas_system"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.gas_system.value} />
                )}
              />
              <form.AppField
                name="stove"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.stove.value} />
                )}
              />
              <form.AppField
                name="flag"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.flag.value} />
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
