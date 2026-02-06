import { type Dictionary, t } from "intlayer";
import { S } from "node_modules/vitest/dist/chunks/config.d.Cy95HiCx";

interface EquipmentContent {
  equipment: string;
  markings: string;
  anchors1: string;
  anchors2: string;
  anchors3: string;
  sea_anchor: string;
  lines1: string;
  lines2: string;
  lines34: string;
  towline: string;
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
  recommended: string;
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
    anchors1: t({
      en: "Anchors",
      fi: "Ankkurivarustus: 2 ankkuria, kummassakin 80 m köyttä tai 60 m kettinkiä",
      sv: "Ankarutrustning: 2 ankare, vardera med 80 m lina eller 60 m kätting",
    }),
    anchors2: t({
      en: "Anchors",
      fi: "Ankkurivarustus: 2 ankkuria, kummassakin 50 m köyttä tai 30 m kettinkiä",
      sv: "Ankarutrustning: 2 ankare, vardera med 50 m lina eller 30 m kätting",
    }),
    anchors3: t({
      en: "Anchors",
      fi: "Ankkurivarustus: Ankkuri ja 30 m köyttä tai kettinkiä",
      sv: "Ankarutrustning: Ankare och 30 m lina eller kätting",
    }),
    sea_anchor: t({
      en: "Sea Anchor",
      fi: "Ajoankkuri",
      sv: "Drivankare",
    }),
    lines1: t({
      en: "Boat Lines 30 m each",
      fi: "Veneköydet, 4 kpl á 30m",
      sv: "Båtlinor, 4 st á 30m",
    }),
    lines2: t({
      en: "Boat Lines 30 m each",
      fi: "Veneköydet, 2 kpl á 30m",
      sv: "Båtlinor, 2 st á 30m",
    }),
    lines34: t({
      en: "Boat Lines 30 m each",
      fi: "Veneköysi, 30m",
      sv: "Båtlina, 30m",
    }),
    towline: t({
      en: "Towing line recommended",
      fi: "Erillistä hinausköyttä suositellaan",
      sv: "Separat bogserlina rekommenderas",
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
      sv: "Fendrar",
    }),
    ladders: t({
      en: "Boat Ladders/boarding",
      fi: "Venetikkaat/veneeseen nousu",
      sv: "Båtstege/ombord stigning",
    }),
    defroster: t({
      en: "Defroster and wipers in enclosed pilothouse",
      fi: "Huurteenpoisto ja lasinpyyhin suljetussa ohjaamossa",
      sv: "Defroster och vindrutetorkare i slutna styrhytter",
    }),
    toilet: t({
      en: "Toilet and waste management",
      fi: "Käymälävarustus ja jätehuolto",
      sv: "Toalettarrangemang och avfallshantering",
    }),
    gas_system: t({
      en: "Gas system, if installed",
      fi: "Nestekaasujärjestelmä, mikäli asennettu",
      sv: "Flytgassystem, ifall installerad",
    }),
    stove: t({
      en: "Stove and heater, if installed",
      fi: "Liesi ja lämmitin, mikäli asennettu",
      sv: "Båtkök och värmare, ifall installerad",
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
    recommended: t({
      en: " RECOMMENDED",
      fi: " SUOSITUS",
      sv: " REKOMMENDATION",
    }),
  },
} satisfies Dictionary<EquipmentContent>;
