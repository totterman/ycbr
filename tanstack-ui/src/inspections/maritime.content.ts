import { type Dictionary, t } from "intlayer";

interface MaritimeContent {
  maritime: string;
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
}

export default {
  key: "maritime",
  content: {
    maritime: t({
      en: "Maritime Equipment",
      fi: "Merenkulkuvarusteet",
      sv: "Sjöfartsutrustning",
    }),
    lights: t({
      en: "Navigation and Anchor Lights",
      fi: "Kulkuvalot ja ankkurivalo",
      sv: "Gångljus och ankarljus",
    }),
    dayshapes: t({
      en: "Day Shapes",
      fi: "Merkkikuviot",
      sv: "Dagersignaler",
    }),
    horn: t({
      en: "Signal Horn",
      fi: "Äänimerkinantolaite",
      sv: "Signalhorn",
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
      sv: "Reservkompass och bäringskompass",
    }),
    log: t({
      en: "Log, Sonar, and Barometer",
      fi: "Loki, kaikuluotain ja ilmapuntari",
      sv: "Logg, ekolod och barometer",
    }),
    charts: t({
      en: "Charts, Instrumets, and Binoculars",
      fi: "Merikartat ja työskentelyvälineet sekä kiikari",
      sv: "Sjökort och bestick samt kikare",
    }),
    radio: t({
      en: "Radio Receiver",
      fi: "Yleisradiovastaanotin",
      sv: "Radiomottagare",
    }),
    satnav: t({
      en: "Satellite Navigation and AIS",
      fi: "Satelliittipaikannin ja AIS",
      sv: "Satellitnavigering och AIS",
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
      sv: "Sjö-VHF",
    }),
    hand_vhf: t({
      en: "Handheld VHF",
      fi: "Käsi-VHF",
      sv: "Hand-VHF",
    }),
    documents: t({
      en: "Boat Documents",
      fi: "Veneen asiakirjat",
      sv: "Båtens dokument",
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
} satisfies Dictionary<MaritimeContent>;
