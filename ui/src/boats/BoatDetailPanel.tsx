import { Button, FormGroup, Stack, Divider, Typography } from "@mui/material";
import { MRT_Row } from "material-react-table";
import { BoatType, drives, Engine, materials, useUpdateBoat } from "./boat";
import { formOptions } from "@tanstack/react-form";

import { useIntlayer, useLocale } from "react-intlayer";
import { useAppForm } from "@/inspections/form/FormHook";
import { noop } from "@tanstack/react-query";

export function BoatDetailPanel({ row }: { row: MRT_Row<BoatType> }) {
  const defaultBoat: BoatType = row.original;
  const content = useIntlayer("boats");
  const { locale } = useLocale();

  const boatOptions = formOptions({
    defaultValues: defaultBoat,
  });
  const { mutateAsync: updateBoat } = useUpdateBoat();

  const form = useAppForm({
    ...boatOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      const boat: BoatType = value as BoatType;
      await updateBoat(boat);
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
        <FormGroup
          sx={{
            position: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
          }}
        >
          <Stack direction="row" spacing={4} sx={{ width: 0.8 }}>
            <Stack direction="column" spacing={1} sx={{ width: 0.4 }}>
              <form.AppField
                name="club"
                children={(field) => <field.YcbrTextField label="Klubb" />}
              />
              <form.AppField
                name="name"
                children={(field) => (
                  <field.YcbrTextField label="Båtens namn" />
                )}
              />
              <form.AppField
                name="owner"
                children={(field) => <field.YcbrTextField label="Ägare 1" />}
              />
            </Stack>

            <Stack direction="column" spacing={1} sx={{ width: 0.4 }}>
              <form.AppField
                name="cert"
                children={(field) => (
                  <field.YcbrTextField label="Båtcertifikatets nummer" />
                )}
              />
              <form.AppField
                name="make"
                children={(field) => (
                  <field.YcbrTextField label="Båttyp/modell" />
                )}
              />
              <form.AppField
                name="owner2"
                children={(field) => (
                  <field.YcbrTextField label="Ägare / Innehavare 2" />
                )}
              />
              <Stack direction="row" spacing={2}>
                <form.AppField
                  name="sign"
                  children={(field) => (
                    <field.YcbrTextField label="Registernummer" />
                  )}
                />
                <form.AppField
                  name="sailnr"
                  children={(field) => (
                    <field.YcbrTextField label="Segelnummer" />
                  )}
                />
              </Stack>
              <form.AppField
                name="hullnr"
                children={(field) => (
                  <field.YcbrTextField label="Skrovnummer" />
                )}
              />
            </Stack>
          </Stack>

          <Stack direction="column" spacing={1} sx={{ width: 1, mt: 4 }}>
            <Stack direction="row" spacing={2}>
              <form.AppField
                name="material"
                children={(field) => (
                  <field.YcbrTextSelect label="Skrovmaterial" options={materials} />
                )}
              />
              <form.AppField
                name="year"
                children={(field) => (
                  <field.YcbrTextField label="Tillverkningsår" />
                )}
              />
              <form.AppField
                name="colour"
                children={(field) => <field.YcbrTextField label="Färg" />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="loa"
                children={(field) => <field.YcbrTextField label="Längd" />}
              />
              <form.AppField
                name="beam"
                children={(field) => <field.YcbrTextField label="Bredd" />}
              />
              <form.AppField
                name="draft"
                children={(field) => <field.YcbrTextField label="Djup" />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="height"
                children={(field) => (
                  <field.YcbrTextField label="Största höjd" />
                )}
              />
              <form.AppField
                name="deplacement"
                children={(field) => (
                  <field.YcbrTextField label="Deplacement" />
                )}
              />
              <form.AppField
                name="sailarea"
                children={(field) => <field.YcbrTextField label="Segelyta" />}
              />
              <form.AppField
                name="drive"
                children={(field) => <field.YcbrTextSelect label="Drev" options={drives} />}
              />
            </Stack>

          </Stack>

          <Stack direction="column" spacing={1} sx={{ width: 1, mt: 6 }}>
            <form.Field name="engines" mode="array">
              {(field) => {
                const eng = field.state.value;
                const blank: Engine = { pos: 0, year: '', make: '', model: '', serial: '', power: 0 };
                const emax = !eng ? 0 : eng.length < 1 ? 0 : eng.reduce((prev, curr) => (curr.pos > prev.pos ? curr : prev), blank).pos;
                console.log('emax:', emax);
                return (
                  <div>
                    {(field.state.value ?? []).map((_, i) => {
                      return (
                        <form.Field key={i} name={`engines[${i}]`}>
                          {(subField) => {
                            return (
                              <Stack direction="row" spacing={2} sx={{ mt: 1 }}>
                                
                                <form.AppField
                                  name={`engines[${i}].year`}
                                  children={(field) => (
                                    <field.YcbrTextField label={`Motor [${i + 1}] år`} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].make`}
                                  children={(field) => (
                                    <field.YcbrTextField label={`Motor [${i + 1}] märke`} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].model`}
                                  children={(field) => (
                                    <field.YcbrTextField label={`Motor [${i + 1}] modell`} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].power`}
                                  children={(field) => (
                                    <field.YcbrTextField label={`Motor [${i + 1}] effekt (kW)`} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].serial`}
                                  children={(field) => (
                                    <field.YcbrTextField label={`Motor [${i + 1}] serienummer`} />
                                  )}
                                />

                                <Button
                                  onClick={() => field.removeValue(i)}
                                  size="small"
                                  color="warning"
                                  variant="outlined"
                                >
                                  Ta bort
                                </Button>
                              </Stack>
                            );
                          }}
                        </form.Field>
                      );
                    })}
                    <Button
                      onClick={() => field.pushValue({ pos: emax + 1, year: '', make: '', model: '', serial: '', power: 0 })}
                      size="medium"
                      variant="outlined"
                      sx={{ mt: 2, mb: 2 }}
                    >
                      Ny motor
                    </Button>
                  </div>
                );
              }}
            </form.Field>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="fuel"
                children={(field) => (
                  <field.YcbrTextField label="Bränsletankarnas volym" />
                )}
              />
              <form.AppField
                name="water"
                children={(field) => (
                  <field.YcbrTextField label="Vattentankarnas volym" />
                )}
              />
              <form.AppField
                name="septi"
                children={(field) => <field.YcbrTextField label="Septitankarnas volym" />}
              />
              <form.AppField
                name="berths"
                children={(field) => <field.YcbrTextField label="Kojplatser" />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="radio"
                children={(field) => <field.YcbrTextField label="Radioanrop" />}
              />
              <form.AppField
                name="home"
                children={(field) => <field.YcbrTextField label="Hemhamn" />}
              />
              <form.AppField
                name="winter"
                children={(field) => (
                  <field.YcbrTextField label="Vinterförvaringsplats" />
                )}
              />
            </Stack>
          </Stack>
        </FormGroup>

        <Stack
          direction="row"
          justifyContent="left"
          spacing={4}
          sx={{ mt: 2, mb: 8 }}
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
