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
  submit: string;
  reset: string;
}

export default {
  key: "enginedata",
  content: {
    engine: t({
      en: "Engine and systems",
      fi: "Moottori ja sen järjestelmät",
      sv: "Motorn och tillhörande system",
    }),
    installation: t({
      en: "Engine installation and condition",
      fi: "Moottorin asennus ja kunto",
      sv: "Motorns installation och skick",
    }),
    controls: t({
      en: "Engine controls",
      fi: "Moottorin hallintalaitteet ja niiden toimivuus",
      sv: "Motorreglage och deras funktion",
    }),
    fuel_system: t({
      en: "Fuel system (tanks, pipes, valves, filters)",
      fi: "Polttoainejärjestelmä (säiliöt, putkistot, sulkuventtiilit, suodattimet)",
      sv: "Bränslesystem (tankar, rör, ventiler, filter)",
    }),
    cooling: t({
      en: "Cooling (bilges, throughulls, pipes)",
      fi: "Jäähdytysjärjestelmä (pohjakaivot, läpiviennit, putkistot)",
      sv: "Kylsystem (genomföringar, rör)",
    }),
    strainer: t({
      en: "Sea water strainer",
      fi: "Merivesisuodatin",
      sv: "Sjövattenfilter",
    }),
    electrical: t({
      en: "Electric systems",
      fi: "Sähköjärjestelmät",
      sv: "Elsystem",
    }),
    separate_batteries: t({
      en: "Separate start and drift batteries",
      fi: "Erillinen käynnistys- ja käyttöakusto",
      sv: "Separata start- och bruksbatterier",
    }),
    shore_power: t({
      en: "Shore power system condition",
      fi: "Maasähköärjestelmän kunto",
      sv: "Landströmsinstallationens skick ",
    }),
    aggregate: t({
      en: "Power aggregate system (230 V) condition",
      fi: "Aggregaattijärjestelmän (230 V) kunto",
      sv: "Aggregatsystemets (230 V) skick",
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
} satisfies Dictionary<EngineDataContent>;
