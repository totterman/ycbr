import { type Dictionary, t } from "intlayer";

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
        owner: t({
            en: "Owner",
            fi: "Omistaja",
            sv: "Ägare",
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
        deplacement: t({
            en: "Deplacement",
            fi: "Uppouma",
            sv: "Vikt"
        }),
        confirmDelete: t({
            en: "Are you sure you want to delete this boat?",
            fi: "Haluatko varmasti poistaa tämän veneen?",
            sv: "Vill du säkert ta bort den här båten?"
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
    },
} satisfies Dictionary;
export default boatContent;