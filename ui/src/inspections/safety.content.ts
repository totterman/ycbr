import { type Dictionary, t } from "intlayer";

interface SafetyContent {
  safety: string;
  buoyancy: string;
  harness: string;
  sailboats: string;
  motorboats: string;
  lifebuoy: string;
  signalling: string;
  alt_a: string;
  alt_b: string;
  draining: string;
  fixed_handpump: string;
  electric_pump: string;
  fire_ext: string;
  hand_extinguisher: string;
  fire_blanket: string;
  plugs: string;
  flashlight: string;
  firstaid: string;
  spare_steering: string;
  preparedness: string;
  emergency_tools: string;
  reserves: string;
  liferaft: string;
  detector: string;
  submit: string;
  reset: string;
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
      en: "Approved Buoyancy Aid for every soul on board",
      fi: "Hyväksytyt kelluntavarusteet jokaiselle veneessä olijalle",
      sv: "Godkänd flytutrustning för varje ombordvarande",
    }),
    harness: t({
      en: "Harnesses and Fixing Points / Lifelines",
      fi: "Turvavaljaat ja kiinnityspisteet / juoksuköydet",
      sv: "Säkerhetsselen och fästpunkter / livlinor",
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
    lifebuoy: t({
      en: "Life Buoy",
      fi: "Pelastusrengas",
      sv: "Livboj",
    }),
    signalling: t({
      en: "Signalling Devices according to the provisions",
      fi: "Hätäilmoitusvälineet vaatimusten mukaan",
      sv: "Nödsignaler enligt bestämmelserna",
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
      sv: "Tömningsutrustning",
    }),
    fixed_handpump: t({
      en: "Fixed Hand Pump",
      fi: "Kiinteästi asennettu käsipumppu",
      sv: "Fast monterad handpump",
    }),
    electric_pump: t({
      en: "Electric Draining Pump",
      fi: "Koneellinen tyhjennyspumppu",
      sv: "Maskinell tömningspump",
    }),
    fire_ext: t({
      en: "Fire Extinguish Equipment",
      fi: "Sammutusvälineet",
      sv: "Brandsläckningsredskap",
    }),
    hand_extinguisher: t({
      en: "Handheld Extinguisher, if required",
      fi: "Käsisammutin, mikäli vaaditaan",
      sv: "Handsläckare, om det krävs",
    }),
    fire_blanket: t({
      en: "Fire Blanket",
      fi: "Sammutuspeite",
      sv: "Släckningsfilt",
    }),
    plugs: t({
      en: "Emergency Plugs",
      fi: "Hätäsulkimet",
      sv: "Nödpluggar",
    }),
    flashlight: t({
      en: "Flashlight, spare batteries and lamps",
      fi: "Käsivalaisin, varaparistot ja -polttimot",
      sv: "Handlampa, reservbatterier och -lampor",
    }),
    firstaid: t({
      en: "First Aid Kit",
      fi: "Ensiapuvälineet",
      sv: "Förstahjälpsutrustning",
    }),
    spare_steering: t({
      en: "Emergency Steering",
      fi: "Varaohjaus",
      sv: "Nödstyrning",
    }),
    preparedness: t({
      en: "Preparedness for Distress on Sea",
      fi: "Varautuminen hätätilanteisiin merellä",
      sv: "Beredskap för nödläge till havs",
    }),
    emergency_tools: t({
      en: "Emergency Tools",
      fi: "Hätätyökalut",
      sv: "Nödverktyg",
    }),
    reserves: t({
      en: "Reserve Food and Water",
      fi: "Varajuomavesi ja varamuona",
      sv: "Reservmat och -vatten",
    }),
    liferaft: t({
      en: "Life Raft with Equipment",
      fi: "Pelastuslautta varusteineen",
      sv: "Livflotte med utrustning",
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
  },
} satisfies Dictionary<SafetyContent>;
