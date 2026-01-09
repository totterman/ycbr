// File: `ui/src/inspections/form/Card.test.tsx`
import React from "react";
import { render, screen, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

// Mock MUI styled to return the underlying component (ignore styles)
vi.mock("@mui/material/styles", () => {
  const React = require("react");
  return {
    styled: (Comp: any) => (_stylesFn: any) => {
      return (props: any) => React.createElement(Comp, props);
    },
  };
});

// Mock MUI Card to a plain div so tests are deterministic
vi.mock("@mui/material/Card", () => {
  const React = require("react");
  return {
    __esModule: true,
    default: (props: any) => React.createElement("div", props),
  };
});

import { Card } from "./Card";

describe("Card (styled wrapper)", () => {
  beforeEach(() => {
    cleanup();
    vi.clearAllMocks();
  });

  it("renders children", () => {
    render(
      React.createElement(Card, null, React.createElement("span", null, "child-content"))
    );
    expect(screen.getByText("child-content")).toBeTruthy();
  });

  it("forwards props and className", () => {
    render(
      React.createElement(
        Card,
        { "data-testid": "card-test", id: "my-card", className: "my-class" },
        "payload",
      ),
    );
    const el = screen.getByTestId("card-test");
    expect(el).toBeTruthy();
    expect(el).toHaveAttribute("id", "my-card");
    expect(el).toHaveClass("my-class");
    expect(el.textContent).toContain("payload");
  });
});
