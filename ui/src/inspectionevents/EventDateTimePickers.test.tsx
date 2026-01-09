/*
 *
 * Each mock renders a button exposing the label and slotProps.textField
 * values and calls onAccept with a fake value whose format() returns a
 * predictable string. Tests verify that the corresponding _valuesCache
 * keys are set and that validation error props are passed through.
 *
*/

import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@mui/x-date-pickers/DatePicker", () => {
  const React = require("react");
  return {
    DatePicker: ({ label, onAccept, slotProps }: any) =>
      React.createElement(
        "button",
        {
          "data-label": label,
          "data-error": String(!!slotProps?.textField?.error),
          "data-helpertext": slotProps?.textField?.helperText ?? "",
          onClick: () =>
            onAccept &&
            onAccept({
              format: (_fmt: string) => `formatted-${label}`,
            }),
        },
        label,
      ),
  };
});

vi.mock("@mui/x-date-pickers/TimePicker", () => {
  const React = require("react");
  return {
    TimePicker: ({ label, onAccept, slotProps }: any) =>
      React.createElement(
        "button",
        {
          "data-label": label,
          "data-error": String(!!slotProps?.textField?.error),
          "data-helpertext": slotProps?.textField?.helperText ?? "",
          onClick: () =>
            onAccept &&
            onAccept({
              format: (_fmt: string) => `formatted-${label}`,
            }),
        },
        label,
      ),
  };
});

import { DayDatePicker, FromTimePicker, ToTimePicker } from "./EventDateTimePickers";

describe("EventDateTimePickers", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    cleanup();
  });

  it("DayDatePicker sets row._valuesCache.day on accept", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(DayDatePicker, { row, validationErrors: {} }));

    const btn = screen.getByText("Inspection Date");
    fireEvent.click(btn);

    expect(row._valuesCache["day"]).toBe("formatted-Inspection Date");
  });

  it("DayDatePicker forwards validation error props to slotProps.textField", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(DayDatePicker, { row, validationErrors: { day: "required" } }));

    const btn = screen.getByText("Inspection Date");
    expect(btn.getAttribute("data-error")).toBe("true");
    expect(btn.getAttribute("data-helpertext")).toBe("required");
  });

  it("FromTimePicker sets row._valuesCache.starts on accept", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(FromTimePicker, { row, validationErrors: {} }));

    const btn = screen.getByText("Start Time");
    fireEvent.click(btn);

    expect(row._valuesCache["starts"]).toBe("formatted-Start Time");
  });

  it("FromTimePicker forwards validation error props to slotProps.textField", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(FromTimePicker, { row, validationErrors: { starts: "bad" } }));

    const btn = screen.getByText("Start Time");
    expect(btn.getAttribute("data-error")).toBe("true");
    expect(btn.getAttribute("data-helpertext")).toBe("bad");
  });

  it("ToTimePicker sets row._valuesCache.ends on accept", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(ToTimePicker, { row, validationErrors: {} }));

    const btn = screen.getByText("End Time");
    fireEvent.click(btn);

    expect(row._valuesCache["ends"]).toBe("formatted-End Time");
  });

  it("ToTimePicker forwards validation error props to slotProps.textField", () => {
    const row: any = { _valuesCache: {} };
    render(React.createElement(ToTimePicker, { row, validationErrors: { ends: "too late" } }));

    const btn = screen.getByText("End Time");
    expect(btn.getAttribute("data-error")).toBe("true");
    expect(btn.getAttribute("data-helpertext")).toBe("too late");
  });
});
