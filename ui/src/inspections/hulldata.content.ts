import { type Dictionary, t } from "intlayer";

interface HullDataContent {
  hullTitle: string;
  hullCondition: string;
  openings: string;
  material: string;
  keel_rudder: string;
  steering: string;
  drive_shaft_prop: string;
  throughulls: string;
  throughulls_rec: string;
  fall_protection: string;
  heavy_objects: string;
  fresh_water: string;
  fresh_water_rec: string;
  lowest_leak: string;
  leak_help: string;
  submit: string;
  reset: string;
  recommended: string,
}

export default {
  key: "hulldata",
  content: {
    hullTitle: t({
      en: "1. Hull and structural safety",
      fi: "1. Runko ja rakenteellinen turvallisuus",
      sv: "1. Skrov och strukturell säkerhet",
    }),
    hullCondition: t({
      en: "Hull and structure condition",
      fi: "Rungon ja rakenteiden kunto",
      sv: "Skrov och konstruktionernas kondition",
    }),
    openings: t({
      en: "Openings and their closability",
      fi: "Aukot ja niiden suljettavuus",
      sv: "Öppningar och möjlighet att tillsluta dem",
    }),
    material: t({
      en: "Material and surface condition",
      fi: "Materiaalin ja pinnoitteen kunto",
      sv: "Materialets ytbehandlingens kondition",
    }),
    keel_rudder: t({
      en: "Keel and rudder",
      fi: "Köli ja peräsin",
      sv: "Köl och roder",
    }),
    steering: t({
      en: "Steering system",
      fi: "Ohjausjärjestelmä ",
      sv: "Styrsystem",
    }),
    drive_shaft_prop: t({
      en: "Drive, shaft, and propeller",
      fi: "Vetolaitteiston, akselin ja potkurin kunto",
      sv: "Drivsystem, axelns och propellerns kondition",
    }),
    throughulls: t({
      en: "Throughulls, valves, and piping",
      fi: "Runkoläpiviennit, sulkuventtiilit ja putkistot",
      sv: "Skrovgenomföringar, avstängningsventiler och rörsystem",
    }),
    throughulls_rec: t({
      en: "Throughulls, valves, and piping - RECOMMENDATION",
      fi: "Runkoläpiviennit, sulkuventtiilit ja putkistot - SUOSITUS",
      sv: "Skrovgenomföringar, avstängningsventiler och rörsystem - REKOMMENDATION",
    }),
    fall_protection: t({
      en: "Fall protection and fastening devices",
      fi: "Putoamisen ehkäisy ja kiinnityshelat",
      sv: "Fallhinder och förtöjningsbeslag",
    }),
    heavy_objects: t({
      en: "Securing heavy objects",
      fi: "Painavien esineiden kiinnitys",
      sv: "Fastsättning av tunga föremål",
    }),
    fresh_water: t({
      en: "Fresh water system",
      fi: "Makeavesijärjestelmät",
      sv: "Sötvattensystem",
    }),
    fresh_water_rec: t({
      en: "Fresh water system - RECOMMENDATION",
      fi: "Makeavesijärjestelmät - SUOSITUS",
      sv: "Sötvattensystem - REKOMMENDATION",
    }),
    lowest_leak: t({
      en: "Lowest point of leak (cm)",
      fi: "Alin vuotokohta (cm)",
      sv: "Den lägsta läckagepunkten (cm)",
    }),
    leak_help: t({
      en: "measure in cm",
      fi: "mittayksikkö cm",
      sv: "mått i cm",
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
} satisfies Dictionary<HullDataContent>;
