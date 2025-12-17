import { type Dictionary, t } from "intlayer";

interface RoutesPageContent {
    notAuthenticated: string;
    granted: string;
    hi: string;
    about: string;
    author: string;
    app_title: string;
    app_text: string;
    users_title: string;
    users_text: string;
    user_name: string;
    user_sign: string;
    user_password: string;
    user_role: string;
    boats_title: string;
    boats_text: string;
    events_title: string;
    events_text: string;
    inspections_title: string;
    inspections_text: string;
    boatowner_title: string;
    boatowner_text: string;
    inspector_title: string;
    inspector_text: string;
    staff_title: string;
    staff_text: string;
    guest_title: string;
    guest_text: string;
}

export default {
    key: "routes",
    content: {
        notAuthenticated: t({
            en: "You are not authenticated.",
            fi: "Et ole kirjautunut",
            sv: "Du har inte loggat in",
        }),
        granted: t({
            en: "you are granted with roles",
            fi: "sinulla on valtuudet",
            sv: "du har rollerna",
        }),
        hi: t({
            en: "Hi",
            fi: "Moi",
            sv: "Hej",
        }),
        about: t({
            en: "This application is a show-case for a React + Vite + Tanstack app consuming a REST API through an OAuth2 BFF.",
            fi: "Sovellus on näyte miten React + Vite + Tanstack -ohjelma käyttää REST APIa OAuth2 -autentikoinnin kautta.",
            sv: "Programmet visar exempel hur en React + Vite + Tanstack -applikation använder REST API genom OAuth2 -auktorisering.",
        }),
        author: t({
            en: "Written by Petri Tötterman, AB Smartbass, 2025",
            fi: "Kirjoittanut Petri Tötterman, AB Smartbass, 2025",
            sv: "Skriven av Petri Tötterman, AB Smartbass, 2025",
        }),
        app_title: t({
            en: "The Boat Inspection App",
            fi: "Veneen katsastussovellus",
            sv: "Båtbesiktnings-appen",
        }),
        app_text: t({
            en: "This simple app supports the process of boat inspections at Finnish boat clubs. It is based around the user roles of Boatowner, Inspector, and Staff, the main entities being Boat, Inspection Event, and Inspection. There is a separate OAuth2 authentication provider for maximal security. All system parts run in containers.",
            fi: "Tämä yksinkertainen sovellus auttaa järjestämään suomalaisten veneseurojen venekatsastuksia. Se perustuu käyttäjärooleihin Veneenomistaja, Katsastaja ja Kanslia, jotka käsittelevät tietoja Vene, Katsastustapahtuma, ja Katsastus. Sisäänkirjautuminen tapahtuu erillisen OAuth2 -autentikointipalvelun avulla tietoturvasyistä. Kaikkia järjestelmän osia ajetaan containereina.",
            sv: "Denna enkla app hjälper till att genomföra båtbesiktningar på finska båtklubbar. Den bygger på användarrollerna Båtägare, Besiktningsman och Kanslist, och behandlar data om båtar, Besiktningstillfällen och Besiktningar. Inloggning av användarna sker genom en separat OAuth2 -autentikeringstjänst för maximal datasäkerhet. Alla systemdelar körs i containers.",
        }),
        users_title: t({
            en: "Users",
            fi: "Käyttäjät",
            sv: "Användare",
        }),
        users_text: t({
            en: "Currently, the app is not connected to any user management system. Instead, 5 users in different roles are hardcoded in the Authentication Provider.",
            fi: "Sovellus ei ole yhteydessä mihinkään jäsenrekisteriin. Sen sijaan 5 esimerkkikäyttäjää on kiinteästi määritelty autentikaattoriin.",
            sv: "Appen är inte kopplad till något medlemsregister. Istället finns 5 modellanvändare med olika roller i autentikeringssystemet.",
        }),
        user_name: t({
            en: "Name",
            fi: "Nimi",
            sv: "Namn",
        }),
        user_sign: t({
            en: "Sign",
            fi: "Tunnus",
            sv: "Signum",
        }),
        user_password: t({
            en: "Password",
            fi: "Salasana",
            sv: "Lösenord",
        }),
        user_role: t({
            en: "Role",
            fi: "Rooli",
            sv: "Rolls",
        }),
        boats_title: t({
            en: "Boats",
            fi: "Veneet",
            sv: "Båtar",
        }),
        boats_text: t({
            en: "Currently, the app has no connection to any boat register, hence an AI generated boat register is used as substitute. For whatever reason, the AI decided on water jets instead of real boats.",
            fi: "Sovellus ei ole yhteydessä mihinkään venerekisteriin, vaan käyttää omaa AI:n generoimaa veneluetteloa. Jostain syystä AI päätti luoda vesijettejä oikeiden veneiden sijasta.",
            sv: "Appen är inte kopplad till någon båtregister, utan har en liten AI-genererad båtförteckning. Av någon konstig anledning fastade AI:n för vattenjettar i stället för riktiga båtar.",
        }),
        events_title: t({
            en: "Inspection Event",
            fi: "Katsastustapahtuma",
            sv: "Besiktningstillfälle",
        }),
        events_text: t({
            en: "An Inspection Event has a place and a time interval. Staff creates and updates Inspection Events. Inspectors can register in them, and Boatowners can book inspections for their boats.",
            fi: "Katsastustapahtumalla on paikka ja aikaväli. Kanslia voi luoda tapahtuman ja muuttaa sen tietoja. Katsastajat voivat ilmoittautua tapahtumman, ja veneenomistajat voivat varata veneilleen katsastuksen.",
            sv: "Besiktningstillfällen har en plats och en tidsintervall. Kansliet kan skapa och ändra tillfällen. Besiktningsmän kan anmäla sig till besiktningstillfällen, och Båtägare kan boka besiktning för sina båtar.",
        }),
        inspections_title: t({
            en: "Inspection",
            fi: "Katsastus",
            sv: "Besiktning",
        }),
        inspections_text: t({
            en: "An Inspection is specific for one boat. The Inspector fills in information about the inspected boat.",
            fi: "Katsastus kohdistuu tiettyyn veneeseen. Katsastaja täyttää katsastettavan veneen tiedot.",
            sv: "En besiktning är specifik för en båt. Besikningsmannen fyller i uppgifter on båten som besiktas.",
        }),
        boatowner_title: t({
            en: "Boatowner",
            fi: "Veneenomistaja",
            sv: "Båtägare",
        }),
        boatowner_text: t({
            en: "Boatowners are allowed to see all Boats and Inspection events. They can update all information of their own boats by selecting EDIT at the Boat register table. They can book inspections for their own boats by selecting BOOK at the Inspection Event table.",
            fi: "Veneenomistajat näkevät kaikki veneet ja katsastustapahtumat. He voivat muuttaa kaikkia omien veneidensä tietoja Veneet-taulukossa. He voivat myös varata katsastusajan omille veneilleen Katsastukset-taulukosta.",
            sv: "Båtägare kan se alla båtar och besiktningstillfällen. De kan ändra på informationen om sina egna båtar i tabellen Båtar. De kan boka besiktningstid för sina egna båtar i tabellen Besiktningstillfällen.",
        }),
        inspector_title: t({
            en: "Inspector",
            fi: "Katsastaja",
            sv: "Besiktningsman",
        }),
        inspector_text: t({
            en: "Inspectors are allowed to see all Boats, Inspection Events, and their own Inspections. They can register as Inspectors at an Inspection event. As registered inspectors, they can select uninspected Boats from named Inspection event, and begin a new Inspection. Their Inspections are stored for later access.",
            fi: "Katsastajat näkevät kaikki veneet, katsastustapahtumat ja omat katsastuksensa. He voivat ilmoittautua katsastajiksi Katsastustilaisuudet -taulukosta. Kun katsastaja on ilmoittautunut Katsastustilaisuuteen, se näkyy Työnjako -sivulla, ja viereisessä sarakkeessa on tähän tilaisuuteen katsastusajan varanneet veneet. Valitsemalla Katsastustilaisuus ja Vene, voi katsastaja aloittaa katsastuksen. Kaikki katsastukset jäävät talteen ja näytetään Katsastukset -sivulle. ",
            sv: "Besiktningsmän kan se alla båtar, besiktningstillfällen och sina egna besiktningar. De kan anmäla sig i tabellen Besiktningstillfällen. Anmälningarna syns på sidan Arbetsfördelning, och där finns också båtarna som har tidsbokning till besiktningstillfället. Genom att välja besiktningstillfälle och båt kan besiktningsmannen börja en ny besiktning.",
        }),
        staff_title: t({
            en: "Staff",
            fi: "Kanslia",
            sv: "Kanslist",
        }),
        staff_text: t({
            en: "Staff members are allowed to see, create, update, and delete all Boats, Inspection Events, and Inspections.",
            fi: "Kansliahenkilöt voivat nähdä, luoda, muuttaa ja poistaa kaikkia veneitä, katsastustilaisuuksia ja katsastuksia.",
            sv: "Kanslister kan se, skapa, ändra och ta bort alla båtar, besiktningstillfällen och båtbesiktningar.",
        }),
        guest_title: t({
            en: "Guest",
            fi: "Vieras",
            sv: "Gäst",
        }),
        guest_text: t({
            en: "A Guest may only look at the boat register.",
            fi: "Vieras voi ainoastaan katsella venerekisteriä.",
            sv: "En Gäst kan endast se på båtförteckningen.",
        }),
 },
} satisfies Dictionary<RoutesPageContent>;
