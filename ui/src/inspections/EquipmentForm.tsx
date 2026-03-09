import { formOptions } from "@tanstack/react-form";
import { EquipmentData } from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { Dispatch, SetStateAction, useContext } from "react";
import { CategoryContext, isMotorboat, isOfClass } from "./categorycontext";

export default function EquipmentForm({ equipmentData, setEquipmentData }: {equipmentData: EquipmentData, setEquipmentData: Dispatch<SetStateAction<EquipmentData>>}) {
  const equipmentOptions = formOptions({
    defaultValues: equipmentData,
  });
  const content = useIntlayer("equipment");
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...equipmentOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      console.log("EquipmentData:", value);
    },
    listeners: {
      onBlur: ({ formApi }) => {
        console.log('onBlur:', formApi.state.values);
        setEquipmentData(formApi.state.values);
      }
    },
  });

  const numbers = [0, 1, 2, 4];

  return (
    <Stack>
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
            <Stack direction="column" sx={{ width: 1000 }}>
              {isOfClass(category, '1234') &&
              <form.AppField
                name="markings"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.1" label={content.markings.value} />
                )}
              />}

              {isOfClass(category, '1') &&
              <form.AppField
                name="anchors"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.2" label={content.anchors1.value} />
                )}
              />}
              {isOfClass(category, '2') &&
              <form.AppField
                name="anchors"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.2" label={content.anchors2.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="anchors"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.2" label={content.anchors3.value} />
                )}
              />}
              {isOfClass(category, '4') &&
              <form.AppField
                name="anchors"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.2" label={content.anchors3.value} rec={content.recommended.value} />
                )}
              />}

              {(isOfClass(category, '12')) && (isMotorboat(category)) &&
              <form.AppField
                name="sea_anchor"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.3" label={content.sea_anchor.value} />
                )}
              />}
              
              {isOfClass(category, '1') &&
              <form.AppField
                name="lines"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.4" label={content.lines1.value} rec={content.towline} />
                )}
              />}
              {isOfClass(category, '2') &&
              <form.AppField
                name="lines"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.4" label={content.lines2.value} rec={content.towline} />
                )}
              />}
              {isOfClass(category, '34') &&
              <form.AppField
                name="lines"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.4" label={content.lines34.value} rec={content.towline} />
                )}
              />}

              {isOfClass(category, '1234') &&
              <form.AppField
                name="tools"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.5" label={content.tools.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="paddel"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.6" label={content.paddel.value} rec={content.recommended.value} />
                )}
              />}
              {isOfClass(category, '34') &&
              <form.AppField
                name="paddel"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.6" label={content.paddel.value} />
                )}
              />}

              {isOfClass(category, '123') &&
              <form.AppField
                name="hook"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.7" label={content.hook.value} />
                )}
              />}
              {isOfClass(category, '4') &&
              <form.AppField
                name="hook"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.7" label={content.hook.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '123') &&
              <form.AppField
                name="resque_line"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.8" label={content.resque_line.value} />
                )}
              />}

              {isOfClass(category, '123') &&
              <form.AppField
                name="fenders"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.9" label={content.fenders.value} />
                )}
              />}
              {isOfClass(category, '4') &&
              <form.AppField
                name="fenders"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.9" label={content.fenders.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1234') &&
              <form.AppField
                name="ladders"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.10" label={content.ladders.value} />
                )}
              />}
 
              {isOfClass(category, '12') &&
              <form.AppField
                name="defroster"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.11" label={content.defroster.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="defroster"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.11" label={content.defroster.value} rec={content.recommended.value} />
                )}
              />}
 
              {isOfClass(category, '1234') &&
              <form.AppField
                name="toilet"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.12" label={content.toilet.value} />
                )}
              />}
              {isOfClass(category, '1234') &&
              <form.AppField
                name="gas_system"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.13" label={content.gas_system.value} />
                )}
              />}
              {isOfClass(category, '1234') &&
              <form.AppField
                name="stove"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.14" label={content.stove.value} />
                )}
              />}

              {isOfClass(category, '123') &&
              <form.AppField
                name="flag"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.15" label={content.flag.value} />
                )}
              />}
              {isOfClass(category, '4') &&
              <form.AppField
                name="flag"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="4.15" label={content.flag.value} rec={content.recommended.value} />
                )}
              />}
            </Stack>
        </FormGroup>
      </form>
    </Stack>
  );
}
