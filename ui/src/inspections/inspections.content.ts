import { type Dictionary, insert, t } from "intlayer";

interface InspectionsContent {
  inspections: string;
  no_inspections: string;
  my_inspections: string;
  inspected_boat: string;
  owner: string;
  boat_name: string;
  hull: string;
  rig: string;
  engine: string;
  equipment: string;
  maritime: string,
  safety: string;
  inspection_event: string;
  inspector: string;
  started: string;
  start_inspection: string;
  inspection_completed: string;
  reset: string;
  back: string;
  next: string;
  finish: string;
  done: string;
  complete_step: string;
  step: string;
  already_completed: string;
  dispatch_text: string;
  release: string;
}

export default {
  key: "inspections",
  content: {
    inspections: t({
      en: "Inspections",
      fi: "Katsastukset",
      sv: "Besiktningar",
    }),
    no_inspections: t({
      en: "No inspections",
      fi: "Ei katsastuksia",
      sv: "Inga besiktningar",
    }),
    my_inspections: t({
      en: "My inspections",
      fi: "Omat katsastukset",
      sv: "Egna besiktningar",
    }),
    inspected_boat: t({
      en: "Inspected Boat",
      fi: "Katsastettava vene",
      sv: "Båt som besiktas",
    }),
    owner: t({
      en: "Owner",
      fi: "Omistaja",
      sv: "Ägare",
    }),
    boat_name: t({
      en: "Boat Name",
      fi: "Veneen nimi",
      sv: "Båtens namn",
    }),
    hull: t({
      en: "Hull",
      fi: "Runko",
      sv: "Skrov",
    }),
    rig: t({
      en: "Rig",
      fi: "Riki",
      sv: "Rigg",
    }),
    engine: t({
      en: "Engine",
      fi: "Moottori",
      sv: "Motor",
    }),
    equipment: t({
      en: "Boat Equipment",
      fi: "Veneilyvarusteet",
      sv: "Båtutrustning",
    }),
    maritime: t({
      en: "Maritime Equipment",
      fi: "Merenkulkuvarusteet",
      sv: "Sjöfartsutrustning",
    }),
    safety: t({
      en: "Safety Equipment",
      fi: "Turvallisuusvarusteet",
      sv: "Säkerhetsutrustning",
    }),
    inspection_event: t({
      en: "Inspection Event",
      fi: "Katsastustilaisuus",
      sv: "Besiktningstillfälle",
    }),
    inspector: t({
      en: "Inspector",
      fi: "Katsastaja",
      sv: "Besiktningsman",
    }),
    started: t({
      en: "Started",
      fi: "Aloitettu",
      sv: "Påbörjats",
    }),
    start_inspection: t({
      en: "Start Inspection",
      fi: "Aloita katsastus",
      sv: "Börja besiktningen",
    }),
    inspection_completed: t({
      en: "Inspection completed",
      fi: "Katsastus valmis",
      sv: "Besiktningen färdig",
    }),
    reset: t({
      en: "Reset",
      fi: "Palauta",
      sv: "Återställ",
    }),
    back: t({
      en: "Back",
      fi: "Takaisin",
      sv: "Föregående",
    }),
    next: t({
      en: "Next",
      fi: "Seuraava",
      sv: "Följande",
    }),
    done: t({
      en: "Finish",
      fi: "Päätä",
      sv: "Avsluta",
    }),
    finish: t({
      en: "Completed",
      fi: "Valmis",
      sv: "Färdig",
    }),
    complete_step: t({
      en: "Complete Step",
      fi: "Vaihe valmis",
      sv: "Steget färdigt",
    }),
    step: t({
      en: "Step",
      fi: "Vaihe",
      sv: "Steg",
    }),
    already_completed: t({
      en: "already completed",
      fi: "on jo valmis",
      sv: "redan färdig",
    }),
    dispatch_text: t({
      en: "Select first the inspection event and then the nave of the boat to inspect. Then push the blue button to start the inspection of the selected boat.",
      fi: "Valitse ensin katsastustilaisuus ja sitten katsastettavan veneen nimi. Paina sitten sinistä näppäinta aloittaaksesi valitun veneen katsastuksen.",
      sv: "Välj först besiktningstillfälle och sedan namnet på båten som ska besiktas. Tryck sedan på den blåa knappen för att börja en ny besiktning med den valda båten.",
    }),
    release: t({
      en: "Release",
      fi: "Vapauta",
      sv: "Frigör",
    }),
  },
} satisfies Dictionary<InspectionsContent>;
