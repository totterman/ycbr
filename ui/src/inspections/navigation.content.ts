import { type Dictionary, t } from "intlayer";

interface NavigationContent {
  navigation: string;
  lights: string;
  dayshapes: string;
  horn: string;
  reflector: string;
  compass: string;
  bearing: string;
  log: string;
  charts: string;
  radio: string;
  satnav: string;
  radar: string;
  spotlight: string;
  vhf: string;
  hand_vhf: string;
  documents: string;
  submit: string;
  reset: string;
  recommended: string;
}

export default {
  key: "navigation",
  content: {
    navigation: t({
      en: "Navigation Equipment",
      fi: "Merenkulkuvarusteet",
      sv: "Navigationsutrustning",
    }),
    lights: t({
      en: "Navigation and Anchor Lights",
      fi: "Kulkuvalot ja ankkurivalo",
      sv: "Lanternor och ankarljus",
    }),
    dayshapes: t({
      en: "Day Shapes",
      fi: "Merkkikuviot",
      sv: "Signalfigurer",
    }),
    horn: t({
      en: "Signal Horn",
      fi: "Äänimerkinantolaite",
      sv: "Ljudsignalanordning",
    }),
    reflector: t({
      en: "Radar Reflector",
      fi: "Tutkaheijastin",
      sv: "Radarreflektor",
    }),
    compass: t({
      en: "Steering Compass",
      fi: "Ohjailukompassi",
      sv: "Styrkompass",
    }),
    bearing: t({
      en: "Spare Compass and Bearing Compass",
      fi: "Varakompassi sekä suuntimalaite",
      sv: "Reservkompass samt pejlapparat",
    }),
    log: t({
      en: "Log, Sonar, and Barometer",
      fi: "Loki, kaikuluotain ja ilmapuntari",
      sv: "Logg, ekolod och barometer",
    }),
    charts: t({
      en: "Charts, Instrumets, and Binoculars",
      fi: "Merikartat ja välineet sekä kiikari",
      sv: "Sjökort och bestick samt kikare",
    }),
    radio: t({
      en: "Radio Receiver",
      fi: "Yleisradiovastaanotin",
      sv: "Rundradiomottagare",
    }),
    satnav: t({
      en: "Satellite Positioning",
      fi: "Satelliittipaikannin",
      sv: "Satellitpositionsapparat",
    }),
    radar: t({
      en: "Radar",
      fi: "Tutka",
      sv: "Radar",
    }),
    spotlight: t({
      en: "Spotlight",
      fi: "Valonheitin",
      sv: "Strålkastare",
    }),
    vhf: t({
      en: "Maritime VHF",
      fi: "VHF-meriradiopuhelin",
      sv: "Marin VHF radiotelefon",
    }),
    hand_vhf: t({
      en: "- handheld VHF",
      fi: "- käsi-VHF",
      sv: "- hand-VHF",
    }),
    documents: t({
      en: "Boat Documents",
      fi: "Veneen asiakirjat",
      sv: "Båtdokument",
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
} satisfies Dictionary<NavigationContent>;
