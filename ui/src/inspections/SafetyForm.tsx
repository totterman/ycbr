import { formOptions } from "@tanstack/react-form";
import { InspectionProps, SafetyData, useUpdateInspection } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { useContext } from "react";
import { CategoryContext, isMotorboat, isOfClass, isSailboat } from "./categorycontext";
import ItemHeader from "./form/ItemHeader";

export default function SafetyForm({ data }: InspectionProps) {
  const safety: SafetyData = data.inspection.safetyData;
  const safetyOptions = formOptions({
    defaultValues: safety,
  });
  const content = useIntlayer("safety");
  const { mutateAsync: updateInspection } = useUpdateInspection(
    data.inspectionId,
  );
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...safetyOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspectionClass = category.inspectionClass;
      data.inspection.safetyData = value;
      console.log("SafetyData:", value);
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
          6. {content.safety}
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
                name="buoyancy"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.1"
                    label={content.buoyancy.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1") && isSailboat(category) && (
              <form.AppField
                name="harness"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.2"
                    label={content.harness1.value}
                  />
                )}
              />
            )}
            {((isOfClass(category, "23") && isSailboat(category)) ||
              (isOfClass(category, "1") && isMotorboat(category))) && (
              <form.AppField
                name="harness"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.2"
                    label={content.harness2.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && isMotorboat(category) && (
              <form.AppField
                name="harness"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.2"
                    label={content.harness3.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1") && (
              <form.AppField
                name="lifebuoy"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.3"
                    label={content.lifebuoy1.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "23") && (
              <form.AppField
                name="lifebuoy"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.3"
                    label={content.lifebuoy234.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="lifebuoy"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.3"
                    label={content.lifebuoy234.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            <ItemHeader nr="6.4" label={content.signalling.value} />

            {isOfClass(category, "123") && (
              <form.AppField
                name="signals_a"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.alt_a.value} />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="signals_a"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.alt_a.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "123") && (
              <form.AppField
                name="signals_b"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.alt_b.value} />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="signals_b"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.alt_b.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            <ItemHeader nr="6.5" label={content.draining.value} />

            {isOfClass(category, "1") && (
              <form.AppField
                name="fixed_handpump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.fixed_handpump1.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && (
              <form.AppField
                name="fixed_handpump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.fixed_handpump23.value}
                  />
                )}
              />
            )}
            {(isOfClass(category, "3") ||
              (isOfClass(category, "4") && isMotorboat(category))) && (
              <form.AppField
                name="fixed_handpump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.fixed_handpump23.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}
            {(isOfClass(category, "4") && isSailboat(category)) && (
              <form.AppField
                name="fixed_handpump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.fixed_handpump4.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1") && (
              <form.AppField
                name="electric_pump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.electric_pump.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && (
              <form.AppField
                name="electric_pump"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.electric_pump.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            <ItemHeader nr="6.6" label={content.fire_ext.value} />

            {isOfClass(category, "12") && (
              <form.AppField
                name="hand_extinguisher"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.hand_extinguisher12.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "34") && (
              <form.AppField
                name="hand_extinguisher"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.hand_extinguisher34.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "123") && (
              <form.AppField
                name="fire_blanket"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.fire_blanket.value} />
                )}
              />
            )}

            {isOfClass(category, "123") && (
              <form.AppField
                name="plugs"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.7"
                    label={content.plugs.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="plugs"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.7"
                    label={content.plugs.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1") && (
              <form.AppField
                name="flashlight"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.8"
                    label={content.flashlight1.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && (
              <form.AppField
                name="flashlight"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.8"
                    label={content.flashlight2.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "3") && (
              <form.AppField
                name="flashlight"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.8"
                    label={content.flashlight34.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="flashlight"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.8"
                    label={content.flashlight34.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "123") && (
              <form.AppField
                name="firstaid"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.9"
                    label={content.firstaid.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="firstaid"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.9"
                    label={content.firstaid.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "123") && (
              <form.AppField
                name="spare_steering"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.10"
                    label={content.spare_steering.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "4") && (
              <form.AppField
                name="spare_steering"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.10"
                    label={content.spare_steering.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "123") && <ItemHeader nr="6.11" label={content.preparedness.value} />}

            {isOfClass(category, "123") && isSailboat(category) && (
              <form.AppField
                name="emergency_tools"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.emergency_tools.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "12") && (
              <form.AppField
                name="reserves"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.reserves.value} />
                )}
              />
            )}
            {isOfClass(category, "3") && (
              <form.AppField
                name="reserves"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    label={content.reserves.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1") && (
              <form.AppField
                name="liferaft"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.12"
                    label={content.liferaft.value}
                  />
                )}
              />
            )}
            {isOfClass(category, "2") && (
              <form.AppField
                name="liferaft"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.12"
                    label={content.liferaft.value}
                    rec={content.recommended.value}
                  />
                )}
              />
            )}

            {isOfClass(category, "1234") && (
              <form.AppField
                name="detector"
                children={(field) => (
                  <field.YcbrCheckBoxField
                    nr="6.13"
                    label={content.detector.value}
                  />
                )}
              />
            )}
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
    </Stack>
  );
}
