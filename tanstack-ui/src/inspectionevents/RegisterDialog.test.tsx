// File: `tanstack-ui/src/inspectionevents/RegisterDialog.test.tsx`
import React from "react";
import { render, screen, fireEvent, waitFor, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@/auth/useUser", () => {
  const { vi: _vi } = require("vitest");
  return {
    useUser: _vi.fn(),
  };
});

const mockedAddInspector = vi.fn();

vi.mock("./inspectionevent", () => {
  return {
    useAddInspector: () => ({ mutateAsync: mockedAddInspector }),
  };
});

// Minimal MUI + icon mocks to allow deterministic rendering and querying
vi.mock("@mui/material", () => {
  const React = require("react");
  return {
    Tooltip: ({ children }: any) => React.createElement("span", null, children),
    IconButton: ({ children, ...props }: any) => React.createElement("button", props, children),
    Dialog: ({ open, children }: any) => (open ? React.createElement("div", { "data-testid": "dialog" }, children) : null),
    DialogTitle: ({ children }: any) => React.createElement("h2", null, children),
    DialogContent: ({ children }: any) => React.createElement("div", null, children),
    List: ({ children }: any) => React.createElement("ul", null, children),
    // Render primary and secondary so tests can assert both labels and values
    ListItemText: ({ primary, secondary }: any) =>
      React.createElement("li", null, React.createElement("strong", null, primary), ": ", React.createElement("span", null, secondary)),
    DialogActions: ({ children }: any) => React.createElement("div", null, children),
    Button: ({ children, ...props }: any) => React.createElement("button", props, children),
  };
});

vi.mock("@mui/icons-material/Event", () => {
  const React = require("react");
  return () => React.createElement("span", { "data-testid": "event-icon" }, "E");
});

import { useUser } from "@/auth/useUser";
import RegisterDialog from "./RegisterDialog";

const mockedUseUser = useUser as unknown as vi.Mock;

describe("RegisterDialog", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    cleanup();
    mockedAddInspector.mockResolvedValue(undefined);
  });

  it("renders icon button and opens dialog showing inspector/location/date/time", async () => {
    mockedUseUser.mockImplementation(() => ({ user: { name: "Alice" } }));
    const row = {
      original: {
        id: 10,
        place: "Harbor",
        day: "2025-11-30T00:00:00",
        starts: "2025-11-30T09:00:00",
        ends: "2025-11-30T10:00:00",
      },
    } as any;

    render(React.createElement(RegisterDialog, { row }));

    // Icon button should be present initially
    const iconBtn = screen.getByRole("button");
    expect(iconBtn).toBeTruthy();

    // Open dialog
    fireEvent.click(iconBtn);

    // Dialog title should appear
    await waitFor(() => {
      expect(screen.getByText("Register as Boat Inspector")).toBeTruthy();
    });

    // Inspector name and location should be visible
    expect(screen.getByText("Inspector:")).toBeTruthy();
    expect(screen.getByText("Alice")).toBeTruthy();
    expect(screen.getByText("Location:")).toBeTruthy();
    expect(screen.getByText("Harbor")).toBeTruthy();

    // Date and Time labels should be present
    expect(screen.getByText("Date:")).toBeTruthy();
    expect(screen.getByText("Time:")).toBeTruthy();
  });

  it("calls addInspector with provided id and constructed dto when Register clicked", async () => {
    mockedUseUser.mockImplementation(() => ({ user: { name: "Bob" } }));
    const row = {
      original: {
        id: 42,
        place: "Pier",
        day: "2025-11-30T00:00:00",
        starts: "2025-11-30T09:00:00",
        ends: "2025-11-30T10:00:00",
      },
    } as any;

    render(React.createElement(RegisterDialog, { row }));

    // Open dialog and click Register
    fireEvent.click(screen.getByRole("button"));
    await waitFor(() => screen.getByText("Register as Boat Inspector"));

    const registerBtn = screen.getAllByRole("button").find((b) => b.textContent === "Register");
    expect(registerBtn).toBeTruthy();
    fireEvent.click(registerBtn as Element);

    await waitFor(() => {
      expect(mockedAddInspector).toHaveBeenCalledTimes(1);
    });

    expect(mockedAddInspector).toHaveBeenCalledWith({
      id: 42,
      dto: {
        inspectorName: "Bob",
        message: "Sent by Bob",
      },
    });
  });

  it("uses id -1 when original.id is null and still calls addInspector", async () => {
    mockedUseUser.mockImplementation(() => ({ user: { name: "Carol" } }));
    const row = {
      original: {
        id: null,
        place: "Dock",
        day: "2025-11-30T00:00:00",
        starts: "2025-11-30T09:00:00",
        ends: "2025-11-30T10:00:00",
      },
    } as any;

    render(React.createElement(RegisterDialog, { row }));

    fireEvent.click(screen.getByRole("button"));
    await waitFor(() => screen.getByText("Register as Boat Inspector"));

    const registerBtn = screen.getAllByRole("button").find((b) => b.textContent === "Register");
    expect(registerBtn).toBeTruthy();
    fireEvent.click(registerBtn as Element);

    await waitFor(() => {
      expect(mockedAddInspector).toHaveBeenCalledTimes(1);
    });

    expect(mockedAddInspector).toHaveBeenCalledWith({
      id: -1,
      dto: {
        inspectorName: "Carol",
        message: "Sent by Carol",
      },
    });
  });
});
