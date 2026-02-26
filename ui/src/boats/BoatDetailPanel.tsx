import { Button, FormGroup, Stack } from "@mui/material";
import { BoatType, Engine, useUpdateBoat } from "./boat";
import { formOptions } from "@tanstack/react-form";

import { useIntlayer, useLocale } from "react-intlayer";
import { useAppForm } from "@/inspections/form/FormHook";

export function BoatDetailPanel({ boat, ro = false }: { boat: BoatType, ro: boolean }) {
  const defaultBoat: BoatType = boat;
  const content = useIntlayer("boats");
  const { locale } = useLocale();

  const boatOptions = formOptions({
    defaultValues: defaultBoat,
  });
  const { mutateAsync: updateBoat } = useUpdateBoat();

  const materials = [
    { code: 'G', text: content.grp },
    { code: 'A', text: content.al },
    { code: 'S', text: content.steel },
    { code: 'M', text: content.mahogany },
    { code: 'W', text: content.wood },
    { code: 'O', text: content.other },
  ];

  const drives = [
    { code: 'D', text: content.inboard },
    { code: 'N', text: content.sterndrive },
    { code: 'U', text: content.outboard },
  ];


  const form = useAppForm({
    ...boatOptions,
    // validators:
    onSubmit: async ({ value }) => {
      // Do something with form data
      const updatedBoat: BoatType = value as BoatType;
      await updateBoat(updatedBoat);
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
                children={(field) => <field.YcbrTextField label={content.club.value} ro={ro} />}
              />
              <form.AppField
                name="name"
                children={(field) => (
                  <field.YcbrTextField label={content.boatName.value} ro={ro} />
                )}
              />
              <form.AppField
                name="owner"
                children={(field) => <field.YcbrTextField label={content.owner1.value} ro={ro} />}
              />
            </Stack>

            <Stack direction="column" spacing={1} sx={{ width: 0.4 }}>
              <form.AppField
                name="cert"
                children={(field) => (
                  <field.YcbrTextField label={content.cert.value} ro={ro} />
                )}
              />
              <form.AppField
                name="make"
                children={(field) => (
                  <field.YcbrTextField label={content.make.value} ro={ro} />
                )}
              />
              <form.AppField
                name="owner2"
                children={(field) => (
                  <field.YcbrTextField label={content.owner2.value} ro={ro} />
                )}
              />
              <Stack direction="row" spacing={2}>
                <form.AppField
                  name="sign"
                  children={(field) => (
                    <field.YcbrTextField label={content.boatSign.value} ro={ro} />
                  )}
                />
                <form.AppField
                  name="sailnr"
                  children={(field) => (
                    <field.YcbrTextField label={content.sailnr.value} ro={ro} />
                  )}
                />
              </Stack>
              <form.AppField
                name="hullnr"
                children={(field) => (
                  <field.YcbrTextField label={content.hullnr.value} ro={ro} />
                )}
              />
            </Stack>
          </Stack>

          <Stack direction="column" spacing={1} sx={{ width: 1, mt: 4 }}>
            <Stack direction="row" spacing={2}>
              <form.AppField
                name="material"
                children={(field) => (
                  <field.YcbrTextSelect label={content.material.value} options={materials} ro={ro} />
                )}
              />
              <form.AppField
                name="year"
                children={(field) => (
                  <field.YcbrTextField label={content.build_year.value} ro={ro} />
                )}
              />
              <form.AppField
                name="colour"
                children={(field) => <field.YcbrTextField label={content.colour.value} ro={ro} />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="loa"
                children={(field) => <field.YcbrTextField label={content.loa.value} ro={ro} />}
              />
              <form.AppField
                name="beam"
                children={(field) => <field.YcbrTextField label={content.beam.value} ro={ro} />}
              />
              <form.AppField
                name="draft"
                children={(field) => <field.YcbrTextField label={content.draft.value} ro={ro} />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="height"
                children={(field) => (
                  <field.YcbrTextField label={content.height.value} ro={ro} />
                )}
              />
              <form.AppField
                name="deplacement"
                children={(field) => (
                  <field.YcbrTextField label={content.deplacement.value} ro={ro} />
                )}
              />
              <form.AppField
                name="sailarea"
                children={(field) => <field.YcbrTextField label={content.sailarea.value} ro={ro} />}
              />
              <form.AppField
                name="drive"
                children={(field) => <field.YcbrTextSelect label={content.drive.value} options={drives} ro={ro} />}
              />
            </Stack>

          </Stack>

          <Stack direction="column" spacing={1} sx={{ width: 1, mt: 6 }}>
            <form.Field name="engines" mode="array">
              {(field) => {
                /* this is unnecessary and will be done in backend */
                const eng = field.state.value;
                const blank: Engine = { pos: 0, year: '', make: '', model: '', serial: '', power: 0 };
                const emax = !eng ? 0 : eng.length < 1 ? 0 : eng.reduce((prev, curr) => (curr.pos > prev.pos ? curr : prev), blank).pos;
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
                                    <field.YcbrTextField label={content.motor_year({ nr: i + 1 })} ro={ro} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].make`}
                                  children={(field) => (
                                    <field.YcbrTextField label={content.motor_make({ nr: i + 1 })} ro={ro} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].model`}
                                  children={(field) => (
                                    <field.YcbrTextField label={content.motor_model({ nr: i + 1 })} ro={ro} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].power`}
                                  children={(field) => (
                                    <field.YcbrTextField label={content.motor_power({ nr: i + 1 })} ro={ro} />
                                  )}
                                />
                                <form.AppField
                                  name={`engines[${i}].serial`}
                                  children={(field) => (
                                    <field.YcbrTextField label={content.motor_serial({ nr: i + 1 })} ro={ro} />
                                  )}
                                />

                                {!ro && <Button
                                  onClick={() => field.removeValue(i)}
                                  size="small"
                                  color="warning"
                                  variant="outlined"
                                >
                                  {content.delete}
                                </Button>}
                              </Stack>
                            );
                          }}
                        </form.Field>
                      );
                    })}
                    {!ro && <Button
                      onClick={() => field.pushValue({ pos: emax + 1, year: '', make: '', model: '', serial: '', power: 0 })}
                      size="medium"
                      variant="outlined"
                      sx={{ mt: 2, mb: 2 }}
                    >
                      {content.add_engine}
                    </Button>}
                  </div>
                );
              }}
            </form.Field>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="fuel"
                children={(field) => (
                  <field.YcbrTextField label={content.fuel.value} ro={ro} />
                )}
              />
              <form.AppField
                name="water"
                children={(field) => (
                  <field.YcbrTextField label={content.water.value} ro={ro} />
                )}
              />
              <form.AppField
                name="septi"
                children={(field) => <field.YcbrTextField label={content.septi.value} ro={ro} />}
              />
              <form.AppField
                name="berths"
                children={(field) => <field.YcbrTextField label={content.berths.value} ro={ro} />}
              />
            </Stack>

            <Stack direction="row" spacing={2}>
              <form.AppField
                name="radio"
                children={(field) => <field.YcbrTextField label={content.radio.value} ro={ro} />}
              />
              <form.AppField
                name="home"
                children={(field) => <field.YcbrTextField label={content.home.value} ro={ro} />}
              />
              <form.AppField
                name="winter"
                children={(field) => (
                  <field.YcbrTextField label={content.winter.value} ro={ro} />
                )}
              />
            </Stack>
          </Stack>
        </FormGroup>

        {!ro && <Stack
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
        </Stack>}
      </form>
    </Stack>
  );
}
