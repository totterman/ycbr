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
    fall_protection: string;
    heavy_objects: string;
    fresh_water: string;
    lowest_leak: string;
    leak_help: string;
}

export default {
    key: "hulldata",
    content: {
        hullTitle: t({
            en: "Hull and structural safety",
            fi: "Runko ja rakenteellinen turvallisuus",
            sv: "Skrov och strukturell säkerhet",
        }),
        hullCondition: t({
            en: "Hull and structure condition",
            fi: "Rungon ja rakenteiden kunto",
            sv: "Skrovets och strukturernas skick",
        }),
        openings: t({
            en: "Openings and their closability",
            fi: "Aukot ja niiden suljettavuus",
            sv: "Öppningar och hur de går att stänga",
        }),
        material: t({
            en: "Material and surface condition",
            fi: "Materiaalin ja pinnoitteen kunto",
            sv: "Materialens och ytbeläggningens skick",
        }),
        keel_rudder: t({
            en: "Keel and rudder",
            fi: "Köli ja peräsin",
            sv: "Köl och roder",
        }),
        steering: t({
            en: "Steering system",
            fi: "Ohjausjärjestelmä ",
            sv: "Styrningsanordning",
        }),
        drive_shaft_prop: t({
            en: "Drive, shaft, and propeller",
            fi: "Vetolaitteiston, akselin ja potkurin kunto",
            sv: "Drevets, axelns och propellerns skick",
        }),
        throughulls: t({
            en: "Throughulls, valves, and piping",
            fi: "Runkoläpiviennit, sulkuventtiilit ja putkistot",
            sv: "Genomförningar, ventiler och rörsystem"
        }),
        fall_protection: t({
            en: "Fall protection and fastening devices",
            fi: "Putoamisen ehkäisy ja kiinnityshelat",
            sv: "Fallhinder och fastsättningsbeslag"
        }),
        heavy_objects: t({
            en: "Securing heavy objects",
            fi: "Painavien esineiden kiinnitys",
            sv: "Fastsättning av tunga föremål",
        }),
        fresh_water: t({
            en: "Fresh water system",
            fi: "Makeavesijärjestelmät",
            sv: "Färskvattensystem",
        }),
        lowest_leak: t({
            en: "Lowest point of leak",
            fi: "Alin vuotokohta",
            sv: "Lägsta läckagepunkten"
        }),
        leak_help: t({
            en: "measure in cm",
            fi: "mittayksikkö cm",
            sv: "mått i cm"
        }),
 },
} satisfies Dictionary<HullDataContent>;

