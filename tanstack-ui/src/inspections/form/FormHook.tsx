import { lazy } from "react";
import { fieldContext, formContext, useFormContext } from "./formContext.js";
import { createFormHook } from "@tanstack/react-form";
import { Button } from "@mui/material";

const MuiTextField = lazy(() => import("./TextField.tsx"));
const MuiCheckBoxField = lazy(() => import("./CheckBoxField.tsx"));

function SubscribeButton({ label }: { label: string }) {
  const form = useFormContext();
  return (
    <form.Subscribe selector={(state) => state.isSubmitting}>
      {(isSubmitting) => (
        <Button type="submit" disabled={isSubmitting} variant="contained">
          {label}
        </Button>
      )}
    </form.Subscribe>
  );
}

function ResetButton({ label }: { label: string }) {
  const form = useFormContext();
  return (
    <Button type="reset" onClick={() => form.reset()}>
      {label}
    </Button>
  );
}

export const { useAppForm, withForm, withFieldGroup } = createFormHook({
  fieldComponents: {
    MuiTextField,
    MuiCheckBoxField,
  },
  formComponents: {
    SubscribeButton,
    ResetButton,
  },
  fieldContext,
  formContext,
});
