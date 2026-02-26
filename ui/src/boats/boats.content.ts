import { type Dictionary, insert, t } from "intlayer";

const boatContent = {
  key: "boats",
  content: {
    nameRequired: t({
      en: "Boat Name is Required",
      fi: "Veneen nimi tarvitaan",
      sv: "Båtens namn behövs",
    }),
    ownerRequired: t({
      en: "Boat Owner is Required",
      fi: "Omistajan nimi tarvitaan",
      sv: "Ägarens namn behövs",
    }),
    boatName: t({
      en: "Boat Name",
      fi: "Veneen nimi",
      sv: "Båtens namn",
    }),
    boatSign: t({
      en: "Boat Sign",
      fi: "Rekisteritunnus",
      sv: "Registerbeteckning",
    }),
    boatKind: t({
      en: "Boat Kind",
      fi: "Veneen laji",
      sv: "Båtens slag",
    }),
    owner: t({
      en: "Owner",
      fi: "Omistaja",
      sv: "Ägare",
    }),
    owner1: t({
      en: "Owner 1",
      fi: "Omistaja 1",
      sv: "Ägare 1",
    }),
    owner2: t({
      en: "Owner 2",
      fi: "Omistaja 2",
      sv: "Ägare 2",
    }),
    make: t({
      en: "Make",
      fi: "Valmistaja",
      sv: "Tillverkare",
    }),
    model: t({
      en: "Model",
      fi: "Malli",
      sv: "Modell",
    }),
    year: t({
      en: "Year",
      fi: "Vuosi",
      sv: "År",
    }),
    engine: t({
      en: "Engine",
      fi: "Moottori",
      sv: "Motor",
    }),
    loa: t({
      en: "LOA",
      fi: "Pituus",
      sv: "Längd",
    }),
    beam: t({
      en: "Beam",
      fi: "Leveys",
      sv: "Bredd",
    }),
    draft: t({
      en: "Draft",
      fi: "Syväys",
      sv: "Djupgående",
    }),
    height: t({
      en: "Height",
      fi: "Suurin korkeus",
      sv: "Största höjd",
    }),
    deplacement: t({
      en: "Deplacement",
      fi: "Uppouma",
      sv: "Vikt",
    }),
    confirmDelete: t({
      en: "Are you sure you want to delete this boat?",
      fi: "Haluatko varmasti poistaa tämän veneen?",
      sv: "Vill du säkert ta bort den här båten?",
    }),
    createBoat: t({
      en: "Create New Boat",
      fi: "Luo uusi vene",
      sv: "Skapa ny båt",
    }),
    updateBoat: t({
      en: "Update Boat",
      fi: "Muuta veneen tietoja",
      sv: "Ändra båtens uppgifter",
    }),
    submit: t({
      en: "Submit",
      fi: "Tallenna",
      sv: "Spara",
    }),
    reset: t({
      en: "Reset",
      fi: "Palauta",
      sv: "Återställ",
    }),
    edit: t({
      en: "Edit",
      fi: "Muokkaa",
      sv: "Ändra",
    }),
    delete: t({
      en: "Delete",
      fi: "Poista",
      sv: "Ta bort",
    }),
    motor_boat: t({
      en: "Motor Boat",
      fi: "Moottorivene",
      sv: "Motorbåt",
    }),
    sail_boat: t({
      en: "Sail Boat",
      fi: "Purjevene",
      sv: "Segelbåt",
    }),
    club: t({
      en: "Club",
      fi: "Seura",
      sv: "Klubb",
    }),
    cert: t({
      en: "Boat Certificate Number",
      fi: "Venetodistuksen numero",
      sv: "Båtcertifikatets nummer",
    }),
    sailnr: t({
      en: "Sail number",
      fi: "Purjenumero",
      sv: "Segelnummer",
    }),
    hullnr: t({
      en: "Hull number",
      fi: "Runkonumero",
      sv: "Skrovnummer",
    }),
    build_year: t({
      en: "Build year",
      fi: "Valmistusvuosi",
      sv: "Tillverkningsår",
    }),
    colour: t({
      en: "Colour",
      fi: "Väri",
      sv: "Färg",
    }),
    sailarea: t({
      en: "Sail area",
      fi: "Purjepinta-ala",
      sv: "Segelyta",
    }),
    drive: t({
      en: "Motor",
      fi: "Moottori",
      sv: "Motor",
    }),
    material: t({
      en: "Material",
      fi: "Materiaali",
      sv: "Material",
    }),
    add_engine: t({
      en: "Add Engine",
      fi: "Lisää moottori",
      sv: "Lägg till motor",
    }),
    fuel: t({
      en: "Fuel volume",
      fi: "Polttoainetankkien tilavuus",
      sv: "Bränsletankarnas volym",
    }),
    water: t({
      en: "Water volume",
      fi: "Vesitankkien tilavuus",
      sv: "Vattentankarnas volym",
    }),
    septi: t({
      en: "Septic volume",
      fi: "Septitankkien tilavuus",
      sv: "Septiktankarnas volym",
    }),
    berths: t({
      en: "Berths",
      fi: "Makuupaikkoja",
      sv: "Kojplatser",
    }),
    radio: t({
      en: "Radio call",
      fi: "Radiokutsu",
      sv: "Radioanrop",
    }),
    home: t({
      en: "Home port",
      fi: "Kotisatama",
      sv: "Hemhamn",
    }),
    winter: t({
      en: "Winter store",
      fi: "Talvisäilytyspaikka",
      sv: "Vinterförvaringsplats",
    }),
    inboard: t({
      en: "Inboard",
      fi: "Sisämoottori",
      sv: "Inombord",
    }),
    sterndrive: t({
      en: "Stern drive",
      fi: "Sisäperä",
      sv: "Inu-drev",
    }),
    outboard: t({
      en: "Outboard",
      fi: "Perämoottori",
      sv: "Utombord",
    }),
    grp: t({
      en: "Fiberglass",
      fi: "Lasikuitu",
      sv: "Glasfiber",
    }),
    al: t({
      en: "Aluminium",
      fi: "Alumiini",
      sv: "Aluminium",
    }),
    steel: t({
      en: "Steel",
      fi: "Teräs",
      sv: "Stål",
    }),
    mahogany: t({
      en: "Mahogany",
      fi: "Mahonki",
      sv: "Mahogny",
    }),
    wood: t({
      en: "Other wood",
      fi: "Muu puulaji",
      sv: "Annat träslag",
    }),
    other: t({
      en: "Other material",
      fi: "Muu materiaali",
      sv: "Annat material",
    }),
    motor_year: insert(
      t({
        en: "Engine [{{ nr }}] year",
        fi: "Moottori [{{ nr }}] vuosi",
        sv: "Motor [{{ nr }}] år",
      }),
    ),
    motor_make: insert(
      t({
        en: "Engine [{{ nr }}] make",
        fi: "Moottori [{{ nr }}] merkki",
        sv: "Motor [{{ nr }}] märke",
      }),
    ),
    motor_model: insert(
      t({
        en: "Engine [{{ nr }}] model",
        fi: "Moottori [{{ nr }}] malli",
        sv: "Motor [{{ nr }}] modell",
      }),
    ),
    motor_serial: insert(
      t({
        en: "Engine [{{ nr }}] serial number",
        fi: "Moottori [{{ nr }}] valmistusnumero",
        sv: "Motor [{{ nr }}] tillverkningsnummer",
      }),
    ),
    motor_power: insert(
      t({
        en: "Engine [{{ nr }}] power (kW)",
        fi: "Moottori [{{ nr }}] teho (kW)",
        sv: "Motor [{{ nr }}] effekt (kW)",
      }),
    ),
  },
} satisfies Dictionary;
export default boatContent;
