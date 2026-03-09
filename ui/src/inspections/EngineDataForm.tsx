import { formOptions } from "@tanstack/react-form";
import { EngineData } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { Dispatch, SetStateAction, useContext } from "react";
import { CategoryContext, isMotorboat, isOfClass } from "./categorycontext";

export default function EngineDataForm({ engineData, setEngineData }: { engineData: EngineData, setEngineData: Dispatch<SetStateAction<EngineData>>}) {
  const engineOptions = formOptions({
    defaultValues: engineData,
  });
  const content = useIntlayer("enginedata");
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...engineOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      console.log("Engine Data:", value);
    },
    listeners: {
      onBlur: ({ formApi }) => {
        console.log('onBlur:', formApi.state.values);
        setEngineData(formApi.state.values);
      }
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
      </form>
    </Stack>
  );
}
