import { type Dictionary, t } from "intlayer";

interface I9EventsContent {
  book_inspection: string;
  book_inspection_title: string;
  boat_name: string;
  boat_owner: string;
  location: string;
  date: string;
  time: string;
  cancel: string;
  book: string;
  register_as: string;
  register_title: string;
  register: string;
  unregister_as: string;
  unregister_title: string;
  unregister: string;
  inspector: string;
  inspection_date: string;
  start_time: string;
  end_time: string;
  inspectors: string;
  boats: string;
  create_title: string;
  reset: string;
  back: string;
  next: string;
  finish: string;
  location_required: string;
  date_required: string;
  start_time_required: string;
  end_time_required: string;
  start_before_end: string;
}

export default {
  key: "i9events",
  content: {
    book_inspection: t({
      en: "Book Inspection",
      fi: "Varaa katsastus",
      sv: "Boka besiktning",
    }),
    book_inspection_title: t({
      en: "Book Boat Inspection",
      fi: "Varaa veneen katsastus",
      sv: "Boka båtbesiktning",
    }),
    boat_name: t({
      en: "Boat Name",
      fi: "Veneen nimi",
      sv: "Båtens  namn",
    }),
    boat_owner: t({
      en: "Owner",
      fi: "Omistaja",
      sv: "Ägare",
    }),
    location: t({
      en: "Location",
      fi: "Paikka",
      sv: "Plats",
    }),
    date: t({
      en: "Date",
      fi: "Päivä",
      sv: "Datum",
    }),
    time: t({
      en: "Time",
      fi: "Aika",
      sv: "Tid",
    }),
    cancel: t({
      en: "Cancel",
      fi: "Peru",
      sv: "Avbryt",
    }),
    book: t({
      en: "Book",
      fi: "Varaa",
      sv: "Boka",
    }),
    register_as: t({
      en: "Register as Inspector",
      fi: "Ilmoittaudu katsastajaksi",
      sv: "Anmäl dig som besiktningsman",
    }),
    register_title: t({
      en: "Register as Boat Inspector",
      fi: "Ilmoittaudu katsastajaksi",
      sv: "Anmäl dig som besiktningsman",
    }),
    register: t({
      en: "Register",
      fi: "Ilmoittaudu",
      sv: "Anmäl dig",
    }),
    unregister_as: t({
      en: "Cancel registration",
      fi: "Poista ilmoittautuminen",
      sv: "Ta bort anmälning",
    }),
    unregister_title: t({
      en: "Cancel registration",
      fi: "Poista ilmoittautuminen",
      sv: "Ta bort anmälning",
    }),
    unregister: t({
      en: "Cancel",
      fi: "Poista",
      sv: "Ta bort",
    }),
    inspection_date: t({
      en: "Inspection Date",
      fi: "Päivämäärä",
      sv: "Datum",
    }),
    start_time: t({
      en: "Start Time",
      fi: "Alkaa klo",
      sv: "Börjar kl",
    }),
    end_time: t({
      en: "End Time",
      fi: "Päättyy klo",
      sv: "Slutar kl",
    }),
    inspector: t({
      en: "Inspector",
      fi: "Katsastaja",
      sv: "Besiktningsman",
    }),
    inspectors: t({
      en: "Nr inspectors",
      fi: "Katsastajien lkm",
      sv: "Antal besiktningsmän",
    }),
    boats: t({
      en: "Nr boats",
      fi: "Veneiden lkm",
      sv: "Antal båtar",
    }),
    create_title: t({
      en: "Create New Inspection Event",
      fi: "Tee uusi katsastustapahtuma",
      sv: "Skapa nytt besiktningstillfälle",
    }),
    reset: t({
      en: "Reset",
      fi: "Palauta",
      sv: "Återställ",
    }),
    back: t({
      en: "Back",
      fi: "Takaisin",
      sv: "Föregående",
    }),
    next: t({
      en: "Next",
      fi: "Seuraava",
      sv: "Följande",
    }),
    finish: t({
      en: "Finish",
      fi: "Päätä",
      sv: "Avsluta",
    }),
    location_required: t({
      en: "Location is Required",
      fi: "Paikka on annettava",
      sv: "Plats måste anges",
    }),
    date_required: t({
      en: "Inspection Date is Required",
      fi: "Päivämäärä on annettava",
      sv: "Datum måste anges",
    }),
    start_time_required: t({
      en: "Start Time is Required",
      fi: "Aloitusaika on annettava",
      sv: "Start måste anges",
    }),
    end_time_required: t({
      en: "End Time is Required",
      fi: "Päätösaika on annettava",
      sv: "Avslutning måste anges",
    }),
    start_before_end: t({
      en: "End Time must be after Start Time",
      fi: "Päätösajan on oltava aloitusajan jälkeen",
      sv: "Avslutning måste vara efter start",
    }),
  },
} satisfies Dictionary<I9EventsContent>;
