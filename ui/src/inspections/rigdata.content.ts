import { type Dictionary, t } from "intlayer";

interface RigDataContent {
  rigTitle: string;
  rig: string;
  sails: string;
  storm_sails: string;
  reefing: string;
  submit: string;
  reset: string;
  recommended: string;
}

export default {
  key: "rigdata",
  content: {
    rigTitle: t({
      en: "Rig Condition and Maintenance",
      fi: "Takila ja purjeet, mikäli asennettu",
      sv: "Rigg och segel, ifall monterad",
    }),
    rig: t({
      en: "Rig",
      fi: "Rikin kunto ja ylläpito",
      sv: "Riggens kondition och underhåll",
    }),
    sails: t({
      en: "Sails",
      fi: "Purjeet",
      sv: "Segel",
    }),
    storm_sails: t({
      en: "Storm sails",
      fi: "Myrskypurjeet",
      sv: "Stormsegel",
    }),
    reefing: t({
      en: "Reefing System",
      fi: "Reivausjärjestely ",
      sv: "Revningsanordning",
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
} satisfies Dictionary<RigDataContent>;
