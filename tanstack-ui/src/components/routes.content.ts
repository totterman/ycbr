import { type Dictionary, t } from "intlayer";

interface RoutesPageContent {
    notAuthenticated: string;
    granted: string;
    hi: string;
    about: string;
    author: string;
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
 },
} satisfies Dictionary<RoutesPageContent>;
