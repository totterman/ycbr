import { type Dictionary, insert, t } from "intlayer";

interface RemarksContent {
  remarks: string;
  new_remark: string;
  no_remarks: string;
  enter_remark: string;
  cancel: string;
  save: string;
  remark_item: string;
  remark_text: string;
  check_remark: string;
}

export default {
  key: "remarks",
  content: {
    remarks: t({
      en: "Remarks",
      fi: "Huomautuksia",
      sv: "Anmärkningar"
    }),
    new_remark: t({
      en: "New Remark",
      fi: "Uusi huomautus",
      sv: "Ny anmärkning"
    }),
    no_remarks: t({
      en: "No Remarks",
      fi: "Ei huomautuksia",
      sv: "Inga anmärkningar"
    }),
    enter_remark: t({
      en: "Enter the remarked inspection item number and the remark contents.",
      fi: "Anna katsastuskohdan numero ja huomautuksen sisältö.",
      sv: "Ge besiktningspunktens nummer och anmärkningens innehåll."
    }),
    remark_item: t({
      en: "Item",
      fi: "Kohta",
      sv: "Punkt"
    }),
    remark_text: t({
      en: "Text",
      fi: "Teksti",
      sv: "Text"
    }),
    cancel: t({
      en: "Cancel",
      fi: "Peru",
      sv: "Avbryt"
    }),
    save: t({
      en: "Save",
      fi: "Tallenna",
      sv: "Spara"
    }),
    check_remark: t({
      en: "Check off remark",
      fi: "Kuittaa huomautus",
      sv: "Kvittera bort anmärkning"
    }),
  },
} satisfies Dictionary<RemarksContent>;
