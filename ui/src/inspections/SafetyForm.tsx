import { formOptions } from "@tanstack/react-form";
import { InspectionProps, SafetyData, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { Card } from "./form/Card";
import { FormGrid } from "./form/FormGrid";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import Divider from "@mui/material/Divider";

export default function SafetyForm({ data }: InspectionProps) {
  const maritime: SafetyData = data.inspection.safetyData;
  const equipmentOptions = formOptions({
    defaultValues: maritime,
  });
  const content = useIntlayer("safety");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.inspectionId);

  const form = useAppForm({
    ...equipmentOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.safetyData = value;
      console.log("SafetyData:", value);
      await updateInspection(data);
    },
  });
  const harnesses = [0, 1, 2, 5];
  const lifebuyous = [0, 1, 2];
  const handpumps = [0, 1, 2];
  const extinguishers = [0, 1, 2];
  const flashlights = [0, 1, 2, 3];

  return (
    <FormGrid justifyContent="flex-start">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          form.handleSubmit();
        }}
      >
        <Typography variant="h5" gutterBottom>
          6. {content.safety}
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
                name="buoyancy"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.buoyancy.value} />
                )}
              />
              <form.AppField
                name="harness"
                children={(field) => (
                  <field.NumberSelect
                    label={content.harness.value}
                    numbers={harnesses}
                  />
                )}
              />
              <form.AppField
                name="lifebuoy"
                children={(field) => (
                  <field.NumberSelect
                    label={content.lifebuoy.value}
                    numbers={lifebuyous}
                  />
                )}
              />

              <Typography variant="h6" sx={{ ml: 2, mt: 2 }}>
                {content.signalling}
              </Typography>
              <form.AppField
                name="signals_a"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.alt_a.value} />
                )}
              />
              <form.AppField
                name="signals_b"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.alt_b.value} />
                )}
              />

              <Typography variant="h6" sx={{ ml: 2, mt: 2 }}>
                {content.draining}
              </Typography>
              <form.AppField
                name="fixed_handpump"
                children={(field) => (
                  <field.NumberSelect
                    label={content.fixed_handpump.value}
                    numbers={handpumps}
                  />
                )}
              />
              <form.AppField
                name="electric_pump"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.electric_pump.value} />
                )}
              />

            </Stack>
            <Stack direction="column">

              <Typography variant="h6" sx={{ ml: 2 }}>
                {content.fire_ext}
              </Typography>
              <form.AppField
                name="hand_extinguisher"
                children={(field) => (
                  <field.NumberSelect
                    label={content.hand_extinguisher.value}
                    numbers={extinguishers}
                  />
                )}
              />
              <form.AppField
                name="fire_blanket"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.fire_blanket.value} />
                )}
              />
              <Divider variant="middle" sx={{ mb: 1 }} />

              <form.AppField
                name="plugs"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.plugs.value} />
                )}
              />
              <form.AppField
                name="flashlight"
                children={(field) => (
                  <field.NumberSelect
                    label={content.flashlight.value}
                    numbers={flashlights}
                  />
                )}
              />
              <form.AppField
                name="firstaid"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.firstaid.value} />
                )}
              />
              <form.AppField
                name="spare_steering"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.spare_steering.value}
                  />
                )}
              />

              <Typography variant="h6" sx={{ ml: 2, mt: 2 }}>
                {content.preparedness}
              </Typography>
              <form.AppField
                name="emergency_tools"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.emergency_tools.value}
                  />
                )}
              />
              <form.AppField
                name="reserves"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reserves.value} />
                )}
              />
              <Divider variant="middle" sx={{ mb: 2 }} />

              <form.AppField
                name="liferaft"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.liferaft.value} />
                )}
              />
              <form.AppField
                name="detector"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.detector.value} />
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
