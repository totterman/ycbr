import { type Dictionary, t } from "intlayer";

interface EngineDataContent {
  engine: string;
  installation: string;
  controls: string;
  fuel_system: string;
  cooling: string;
  strainer: string;
  electrical: string;
  separate_batteries: string;
  shore_power: string;
  aggregate: string;
  reserve: string;
  submit: string;
  reset: string;
  recommended: string;
}

export default {
  key: "enginedata",
  content: {
    engine: t({
      en: "Engine and systems",
      fi: "Moottori ja sen järjestelmät",
      sv: "Motorn och dess system, ifall monterade",
    }),
    installation: t({
      en: "Engine installation and condition",
      fi: "Moottorin asennus ja kunto",
      sv: "Motorns installation och kondition",
    }),
    controls: t({
      en: "Engine controls",
      fi: "Moottorin hallintalaitteet ja niiden toimivuus",
      sv: "Motorns reglage och deras funktion",
    }),
    fuel_system: t({
      en: "Fuel system (tanks, pipes, valves, filters)",
      fi: "Polttoainejärjestelmä (säiliöt, putkistot, sulkuventtiilit, suodattimet)",
      sv: "Bränslesystemet (tankar, rör, avstängningsventiler, filter)",
    }),
    cooling: t({
      en: "Cooling (bilges, throughulls, pipes)",
      fi: "Jäähdytysjärjestelmä (pohjakaivot, läpiviennit, putkistot)",
      sv: "Kylsystemet (bottenbrunnar, genomföringar, rör, ventiler)",
    }),
    strainer: t({
      en: "Sea water strainer",
      fi: "Merivesisuodatin",
      sv: "Sjövattenfilter",
    }),
    electrical: t({
      en: "Electric systems",
      fi: "Sähköjärjestelmät",
      sv: "Elsystemet",
    }),
    separate_batteries: t({
      en: "- separate start and drift batteries",
      fi: "- erillinen käynnistys- ja käyttöakusto",
      sv: "- separat start- och förbrukningsbatteribank",
    }),
    shore_power: t({
      en: "- shore power system condition",
      fi: "- maasähköjärjestelmän (230 VAC) kunto, mikäli asennettu",
      sv: "- landströmsystemet (230 VAC), ifall monterat",
    }),
    aggregate: t({
      en: "- power aggregate system (230 V) condition",
      fi: "- aggregaattijärjestelmän (230 V) kunto, mikäli asennettu",
      sv: "- aggregatsystemets (230 V) kondition, ifall monterat",
    }),
    reserve: t({
      en: "- reserve power systemn",
      fi: "- varavoimajärjestelmä",
      sv: "- reservkraftsystem",
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
} satisfies Dictionary<EngineDataContent>;
