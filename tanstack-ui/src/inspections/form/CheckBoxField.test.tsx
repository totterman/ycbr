// File: `tanstack-ui/src/inspections/form/CheckBoxField.test.tsx`
import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

// Mock @tanstack/react-form useStore
vi.mock("@tanstack/react-form", () => {
  return {
    useStore: vi.fn(),
  };
});

// Mock local formContext useFieldContext
vi.mock("./formContext", () => {
  return {
    useFieldContext: vi.fn(),
  };
});

// Lightweight MUI mocks for deterministic rendering
vi.mock("@mui/material", () => {
  const React = require("react");
  return {
    // Render Checkbox as a real input so events behave normally
    Checkbox: (props: any) =>
      React.createElement("input", { type: "checkbox", "data-testid": "mui-checkbox", ...props }),
    // Render FormControlLabel as a label that contains the control and label text
    FormControlLabel: ({ control, label, ...rest }: any) =>
      React.createElement(
        "label",
        { "data-testid": "form-control-label", ...rest },
        control,
        React.createElement("span", { "data-testid": "label-text", style: { marginLeft: 8 } }, label),
      ),
  };
});

import { useStore } from "@tanstack/react-form";
import { useFieldContext } from "./formContext";
import CheckBoxField from "./CheckBoxField";

const mockedUseStore = useStore as unknown as vi.Mock;
const mockedUseFieldContext = useFieldContext as unknown as vi.Mock;

describe("CheckBoxField", () => {
  beforeEach(() => {
    cleanup();
    vi.clearAllMocks();
  });

  it("renders label and checkbox checked state from field.state.value", () => {
    const field = {
      store: {},
      state: { value: true },
      handleChange: vi.fn(),
      handleBlur: vi.fn(),
    };
    mockedUseFieldContext.mockImplementation(() => field);
    mockedUseStore.mockImplementation(() => []); // no errors

    render(React.createElement(CheckBoxField, { label: "My Label" }));

    // label text
    expect(screen.getByTestId("label-text").textContent).toBe("My Label");

    // checkbox checked state
    const checkbox = screen.getByTestId("mui-checkbox") as HTMLInputElement;
    expect(checkbox.checked).toBe(true);
  });

  it("calls handleChange with the new checked value when changed", () => {
    const handleChange = vi.fn();
    const field = {
      store: {},
      state: { value: false },
      handleChange,
      handleBlur: vi.fn(),
    };
    mockedUseFieldContext.mockImplementation(() => field);
    mockedUseStore.mockImplementation(() => []);

    render(React.createElement(CheckBoxField, { label: "Toggle" }));

    const checkbox = screen.getByTestId("mui-checkbox") as HTMLInputElement;
    // simulate checking the box
    fireEvent.change(checkbox, { target: { checked: true } });

    expect(handleChange).toHaveBeenCalledTimes(1);
    expect(handleChange).toHaveBeenCalledWith(true);
  });

  it("calls handleBlur when checkbox loses focus", () => {
    const handleBlur = vi.fn();
    const field = {
      store: {},
      state: { value: false },
      handleChange: vi.fn(),
      handleBlur,
    };
    mockedUseFieldContext.mockImplementation(() => field);
    mockedUseStore.mockImplementation(() => []);

    render(React.createElement(CheckBoxField, { label: "BlurTest" }));

    const checkbox = screen.getByTestId("mui-checkbox") as HTMLInputElement;
    fireEvent.blur(checkbox);

    expect(handleBlur).toHaveBeenCalledTimes(1);
  });

  it("renders validation errors returned from useStore", () => {
    const field = {
      store: {},
      state: { value: false },
      handleChange: vi.fn(),
      handleBlur: vi.fn(),
    };
    mockedUseFieldContext.mockImplementation(() => field);
    mockedUseStore.mockImplementation(() => ["must be true", "other error"]);

    render(React.createElement(CheckBoxField, { label: "Errors" }));

    expect(screen.getByText("must be true")).toBeTruthy();
    expect(screen.getByText("other error")).toBeTruthy();

    // style color is inline "red" in component
    const errEl = screen.getByText("must be true");
    expect(errEl).toHaveStyle({ color: "red" });
  });
});
