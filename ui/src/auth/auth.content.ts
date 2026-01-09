import { type Dictionary, t } from "intlayer";

interface AuthorizationContent {
  loading: string;
  login_error: string;
  logging_in: string;
  login: string;
  logging_out: string;
  logout: string;
}

export default {
  key: "auth",
  content: {
    loading: t({
      en: "Loading",
      fi: "Ladataan",
      sv: "Laddar ner",
    }),
    login_error: t({
      en: "An error occurred during login.",
      fi: "Virhe sisäänkirjautumisessa.",
      sv: "Fel vid inloggning.",
    }),
    logging_in: t({
      en: "Logging in...",
      fi: "Sisäänkirjautuminen...",
      sv: "Inloggning...",
    }),
    login: t({
      en: "Login",
      fi: "Kirjaudu",
      sv: "Logga in",
    }),
    logging_out: t({
      en: "Logging out...",
      fi: "Uloskirjautuminen...",
      sv: "Utloggning...",
    }),
    logout: t({
      en: "Logout",
      fi: "Kirjaudu ulos",
      sv: "Logga ut",
    }),
  },
} satisfies Dictionary<AuthorizationContent>;
