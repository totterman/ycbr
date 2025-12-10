// File: `tanstack-ui/src/inspections/form/formContext.test.ts`
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@tanstack/react-form", () => {
  const { vi: _vi } = require("vitest");
  const createFormHookContexts = _vi.fn(() => {
    return {
      fieldContext: { _mock: "fieldCtx" },
      useFieldContext: () => "useFieldResult",
      formContext: { _mock: "formCtx" },
      useFormContext: () => "useFormResult",
    };
  });
  return { createFormHookContexts };
});

// import after mocking so the module uses the mocked factory
import {
  fieldContext,
  useFieldContext,
  formContext,
  useFormContext,
} from "./formContext";

const rf = require("@tanstack/react-form");

describe("formContext exports", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("calls createFormHookContexts once at module init", () => {
    expect(typeof rf.createFormHookContexts).toBe("function");
    expect(rf.createFormHookContexts).toHaveBeenCalledTimes(1);
  });

  it("exports fieldContext and formContext from the factory", () => {
    expect(fieldContext).toEqual({ _mock: "fieldCtx" });
    expect(formContext).toEqual({ _mock: "formCtx" });
  });

  it("exports useFieldContext and useFormContext that return mocked values", () => {
    expect(useFieldContext()).toBe("useFieldResult");
    expect(useFormContext()).toBe("useFormResult");
  });
});
