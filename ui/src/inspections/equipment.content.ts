import { type Dictionary, t } from "intlayer";

interface EquipmentContent {
  equipment: string;
  markings: string;
  anchors: string;
  sea_anchor: string;
  lines: string;
  tools: string;
  paddel: string;
  hook: string;
  resque_line: string;
  fenders: string;
  ladders: string;
  defroster: string;
  toilet: string;
  gas_system: string;
  stove: string;
  flag: string;
  submit: string;
  reset: string;
}

export default {
  key: "equipment",
  content: {
    equipment: t({
      en: "Boat Equipment",
      fi: "Venevarusteet",
      sv: "Båtutrustning",
    }),
    markings: t({
      en: "Boat and Equipment Marking",
      fi: "Veneen ja varusteiden merkinnät",
      sv: "Märkning av båt och utrustning",
    }),
    anchors: t({
      en: "Anchors",
      fi: "Ankkurivarustus",
      sv: "Ankringsutrustning",
    }),
    sea_anchor: t({
      en: "Sea Anchor",
      fi: "Ajoankkuri",
      sv: "Drivankare",
    }),
    lines: t({
      en: "Boat Lines 30 m each",
      fi: "Veneköydet, á 30m",
      sv: "Båtlinor, á 30m",
    }),
    tools: t({
      en: "Tools and parts",
      fi: "Työkalut ja varaosat",
      sv: "Verktyg och reservdelar",
    }),
    paddel: t({
      en: "Oars and Paddel",
      fi: "Airot ja mela",
      sv: "Åror och paddel",
    }),
    hook: t({
      en: "Boat Hook",
      fi: "Venehaka",
      sv: "Båtshake",
    }),
    resque_line: t({
      en: "Resque Line",
      fi: "Heittoliina",
      sv: "Kastlina",
    }),
    fenders: t({
      en: "Fenders",
      fi: "Laitasuojat",
      sv: "Sidoskydd",
    }),
    ladders: t({
      en: "Boat Ladders/boarding",
      fi: "Venetikkaat/veneeseen nousu",
      sv: "Båtstege/ombordstigning",
    }),
    defroster: t({
      en: "Defroster and wipers in enclosed pilothouse",
      fi: "Huurteenpoisto ja lasinpyyhin suljetussa ohjaamossa",
      sv: "Avfrostning och vindrutetorkare i stängd styrhytt",
    }),
    toilet: t({
      en: "Toilet and waste management",
      fi: "Käymälävarustus ja jätehuolto",
      sv: "Toalett och avfallshantering",
    }),
    gas_system: t({
      en: "Gas system, if installed",
      fi: "Nestekaasujärjestelmä, mikäli asennettu",
      sv: "Flytgassystem, om installerad",
    }),
    stove: t({
      en: "Stove and heater, if installed",
      fi: "Liesi ja lämmitin, mikäli asennettu",
      sv: "Spis och värmare, on installerad",
    }),
    flag: t({
      en: "Flag or pennant",
      fi: "Perälippu tai viiri",
      sv: "Akterflagga eller vimpel",
    }),
    submit: t({
      en: "Save",
      fi: "Tallenna",
      sv: "Spara",
    }),
    reset: t({
      en: "Reset",
      fi: "Peruuta",
      sv: "Återställ",
    }),
  },
} satisfies Dictionary<EquipmentContent>;
