import { Locales, type IntlayerConfig } from "intlayer";

const config: IntlayerConfig = {
    internationalization: {
        locales: [
            Locales.ENGLISH,
            Locales.FINNISH_FINLAND,
            Locales.SWEDISH_FINLAND,
        ],
        defaultLocale: Locales.SWEDISH_FINLAND,
    },
//    build: {
//        optimize: true,
//        importMode: "dynamic",
//    },
    log: {
        mode: "verbose",
    }
};
export default config;