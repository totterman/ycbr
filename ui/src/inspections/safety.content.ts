import { type Dictionary, t } from "intlayer";

interface SafetyContent {
  safety: string;
  buoyancy: string;
  harness1: string;
  harness2: string;
  harness3: string;
  sailboats: string;
  motorboats: string;
  lifebuoy1: string;
  lifebuoy234: string;
  signalling: string;
  alt_a: string;
  alt_b: string;
  draining: string;
  fixed_handpump1: string;
  fixed_handpump23: string;
  fixed_handpump4: string;
  electric_pump: string;
  fire_ext: string;
  hand_extinguisher12: string;
  hand_extinguisher34: string;
  fire_blanket: string;
  plugs: string;
  flashlight1: string;
  flashlight2: string;
  flashlight34: string;
  firstaid: string;
  spare_steering: string;
  preparedness: string;
  emergency_tools: string;
  reserves: string;
  liferaft: string;
  detector: string;
  submit: string;
  reset: string;
  recommended: string;
}

export default {
  key: "safety",
  content: {
    safety: t({
      en: "Safety Equipment",
      fi: "Turvallisuusvarusteet",
      sv: "Säkerhetsutrustning",
    }),
    buoyancy: t({
      en: "Buoyancy Aid",
      fi: "Kelluntavarusteet",
      sv: "Flytutrustning",
    }),

    harness1: t({
      en: "Harnesses and Fixing Points / Lifelines for every soul onboard",
      fi: "Turvavaljaat ja kiinnityspisteet / juoksuköydet kaikille",
      sv: "Säkerhetsselen och fästpunkter / linor för alla ombord",
    }),
    harness2: t({
      en: "Harnesses and Fixing Points / Lifelines 2 pcs",
      fi: "Turvavaljaat ja kiinnityspisteet / juoksuköydet 2 kpl",
      sv: "Säkerhetsselen och fästpunkter / linor 2 st",
    }),
    harness3: t({
      en: "Harness and Fixing Point / Lifeline",
      fi: "Turvavaljaat ja kiinnityspiste / juoksuköysi",
      sv: "Säkerhetssele och fästpunkt / lina",
    }),
    
    sailboats: t({
      en: "Sailboats",
      fi: "Purjeveneet",
      sv: "Segelbåtar",
    }),
    motorboats: t({
      en: "Motorboats",
      fi: "Moottoriveneet",
      sv: "Motorbåtar",
    }),
    lifebuoy1: t({
      en: "Life Buoy 2pcs",
      fi: "Pelastusrengas 2 kpl varusteineen",
      sv: "Livboj 2 st med utrustning",
    }),
    lifebuoy234: t({
      en: "Life Buoy",
      fi: "Pelastusrengas varusteineen",
      sv: "Livboj med utrustning",
    }),
    signalling: t({
      en: "Signalling Devices according to the provisions",
      fi: "Hätäilmoitusvälineet vaatimusten mukaan",
      sv: "Medel för nödanmälan enligt regler",
    }),
    alt_a: t({
      en: "Option A",
      fi: "Vaihtoehto A",
      sv: "Alternativ A",
    }),
    alt_b: t({
      en: "Option B",
      fi: "Vaihtoehto B",
      sv: "Alternativ B",
    }),
    draining: t({
      en: "Draining devices",
      fi: "Tyhjennysvälineet",
      sv: "Länsningsanordningar",
    }),
    fixed_handpump1: t({
      en: "Fixed Hand Pump 2 pcs",
      fi: "Käsipumppu 2 kpl, kiinteästi asennetut",
      sv: "Handpump 2 st, fast monterade",
    }),
    fixed_handpump23: t({
      en: "Fixed Hand Pump",
      fi: "Käsipumppu, kiinteästi asennettu",
      sv: "Handpump, fast monterad",
    }),
    fixed_handpump4: t({
      en: "Temporary devices",
      fi: "Tilapäiset tyhjennysvälineet: sanko, äyskäri, käsipumppu",
      sv: "Tillfällig tömningsutrustning: hink, öskar, handpump",
    }),
    electric_pump: t({
      en: "Electric Draining Pump",
      fi: "Koneellinen tyhjennyspumppu",
      sv: "Maskinell länspump",
    }),
    fire_ext: t({
      en: "Fire Extinguish Equipment",
      fi: "Sammutusvälineet",
      sv: "Släckningsutrustning",
    }),

    hand_extinguisher12: t({
      en: "Handheld Extinguisher 2 pcs, if required",
      fi: "Käsisammutin 2 kpl, mikäli vaaditaan",
      sv: "Handsläckare 2 st, om så fordras",
    }),
    hand_extinguisher34: t({
      en: "Handheld Extinguisher, if required",
      fi: "Käsisammutin, mikäli vaaditaan",
      sv: "Handsläckare, om så fordras",
    }),

    fire_blanket: t({
      en: "Fire Blanket",
      fi: "Sammutuspeite",
      sv: "Släckningsfilt",
    }),
    plugs: t({
      en: "Emergency Plugs",
      fi: "Hätäsulkimet",
      sv: "Nödtillslutare",
    }),

    flashlight1: t({
      en: "Flashlight 3 pcs, spare batteries and -lamps",
      fi: "Käsivalaisin 3 kpl, varaparistot ja -polttimot",
      sv: "Ficklampa 3 st, reservbatterier och -lampor",
    }),
    flashlight2: t({
      en: "Flashlight 2 pcs, spare batteries and lamps",
      fi: "Käsivalaisin 2 kpl, varaparistot ja -polttimot",
      sv: "Ficklampa 2 st, reservbatterier och -lampor",
    }),
    flashlight34: t({
      en: "Flashlight, spare batteries and lamps",
      fi: "Käsivalaisin, varaparistot ja -polttimot",
      sv: "Ficklampa, reservbatterier och -lampor",
    }),

    firstaid: t({
      en: "First Aid Kit",
      fi: "Ensiapuvälineet",
      sv: "Förstahjälpsutrustning",
    }),
    spare_steering: t({
      en: "Emergency Steering",
      fi: "Varaohjausjärjestelmä",
      sv: "Reservstyranordning",
    }),
    preparedness: t({
      en: "Preparedness for Distress on Sea",
      fi: "Varautuminen hätätilanteisiin merellä",
      sv: "Förberedd för nödsituationer till sjöss",
    }),
    emergency_tools: t({
      en: "Emergency Tools",
      fi: "Hätätyökalut",
      sv: "Nödverktyg",
    }),
    reserves: t({
      en: "Reserve Food and Water",
      fi: "Varajuomavesi ja varamuona",
      sv: "Dricksvatten och proviant i reserv",
    }),
    liferaft: t({
      en: "Life Raft with Equipment",
      fi: "Pelastuslautta varusteineen",
      sv: "Räddningsflotte med utrustning",
    }),
    detector: t({
      en: "Fire Detector",
      fi: "Palonilmaisin",
      sv: "Brandvarnare",
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
} satisfies Dictionary<SafetyContent>;
