import { type Dictionary, t } from "intlayer";

interface ComponentsPageContent {
  loading: string;
  home: string;
  about: string;
  boats: string;
  i9events: string;
  dispatch: string;
  inspections: string;
  my_boats: string;
  my_inspections: string;
  office: string;
}

export default {
  key: "components",
  content: {
    loading: t({
      en: "Loading",
      fi: "Ladataan",
      sv: "Laddar new",
    }),
    home: t({
      en: "Home",
      fi: "Aloitussivu",
      sv: "Startsida",
    }),
    about: t({
      en: "About",
      fi: "Taustoja",
      sv: "Angående",
    }),
    boats: t({
      en: "Boats",
      fi: "Veneet",
      sv: "Båtar",
    }),
    i9events: t({
      en: "Inspection Events",
      fi: "Katsastustilaisuudet",
      sv: "Besiktningstillfällen",
    }),
    dispatch: t({
      en: "Dispatch",
      fi: "Työnjako",
      sv: "Arbetsfördelning",
    }),
    inspections: t({
      en: "Boat Inspections",
      fi: "Katsastukset",
      sv: "Besiktningar",
    }),
    my_boats: t({
      en: "My Boats",
      fi: "Veneeni",
      sv: "Mina båtar",
    }),
    my_inspections: t({
      en: "My Inspections",
      fi: "Katsastukseni",
      sv: "Mina besiktningar",
    }),
    office: t({
      en: "Office",
      fi: "Toimisto",
      sv: "Kansli",
    }),
  },
} satisfies Dictionary<ComponentsPageContent>;
