import { type Dictionary, t } from "intlayer";

interface RigDataContent {
    rigTitle: string;
    rig: string;
    sails: string;
    storm_sails: string;
    reefing: string;
}

export default {
    key: "rigdata",
    content: {
        rigTitle: t({
            en: "Rig",
            fi: "Riki",
            sv: "Rigg",
        }),
        rig: t({
            en: "Rig Condition and Maintenance",
            fi: "Takila ja purjeet, mikäli asennettu",
            sv: "Rigg och segel, om monterade",
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
            sv: "Revanordning",
        }),
 },
} satisfies Dictionary<RigDataContent>;

