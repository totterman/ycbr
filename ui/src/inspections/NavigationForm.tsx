import { formOptions } from "@tanstack/react-form";
import {
  InspectionProps,
  NavigationData,
  useUpdateInspection,
} from "./inspection";
import Typography from "@mui/material/Typography";
import FormGroup from "@mui/material/FormGroup";
import { useAppForm } from "./form/FormHook";
import { useIntlayer } from "react-intlayer";
import Stack from "@mui/material/Stack";
import { useContext } from "react";
import { CategoryContext, isMotorboat, isOfClass } from "./categorycontext";

export default function NavigationForm({ data }: InspectionProps) {
  const navigation: NavigationData = data.inspection.navigationData;
  const navigationOptions = formOptions({
    defaultValues: navigation,
  });
  const content = useIntlayer("navigation");
  const { mutateAsync: updateInspection } = useUpdateInspection(data.inspectionId);
  const category = useContext(CategoryContext);

  const form = useAppForm({
    ...navigationOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      data.inspection.navigationData = value;
      console.log("NavigationData:", value);
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
          5. {content.navigation}
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
                name="lights"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.1" label={content.lights.value} />
                )}
              />}
              {isOfClass(category, '1234') &&
              <form.AppField
                name="dayshapes"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.2" label={content.dayshapes.value} />
                )}
              />}
              {isOfClass(category, '1234') &&
              <form.AppField
                name="horn"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.3" label={content.horn.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="reflector"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.4" label={content.reflector.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="reflector"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.4" label={content.reflector.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1234') &&
              <form.AppField
                name="compass"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.5" label={content.compass.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="bearing"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.6" label={content.bearing.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="bearing"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.6" label={content.bearing.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="log"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.7" label={content.log.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="log"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.7" label={content.log.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '123') &&
              <form.AppField
                name="charts"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.8" label={content.charts.value} />
                )}
              />}
              {isOfClass(category, '4') &&
              <form.AppField
                name="charts"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.8" label={content.charts.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="radio"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.9" label={content.radio.value} />
                )}
              />}
              {isOfClass(category, '34') &&
              <form.AppField
                name="radio"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.9" label={content.radio.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1') &&
              <form.AppField
                name="satnav"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.10" label={content.satnav.value} />
                )}
              />}
              {isOfClass(category, '2') &&
              <form.AppField
                name="satnav"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.10" label={content.satnav.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1') &&
              <form.AppField
                name="radar"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.11" label={content.radar.value} />
                )}
              />}
              {isOfClass(category, '2') &&
              <form.AppField
                name="radar"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.11" label={content.radar.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '12') && isMotorboat(category) &&
              <form.AppField
                name="spotlight"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.12" label={content.spotlight.value} />
                )}
              />}
              {isOfClass(category, '3') && isMotorboat(category) &&
              <form.AppField
                name="spotlight"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.12" label={content.spotlight.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '12') &&
              <form.AppField
                name="vhf"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.13" label={content.vhf.value} />
                )}
              />}
              {isOfClass(category, '3') &&
              <form.AppField
                name="vhf"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.13" label={content.vhf.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1') &&
              <form.AppField
                name="hand_vhf"
                children={(field) => (
                  <field.YcbrCheckBoxField label={content.hand_vhf.value} rec={content.recommended.value} />
                )}
              />}

              {isOfClass(category, '1234') &&
              <form.AppField
                name="documents"
                children={(field) => (
                  <field.YcbrCheckBoxField nr="5.14" label={content.documents.value} />
                )}
              />}
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
