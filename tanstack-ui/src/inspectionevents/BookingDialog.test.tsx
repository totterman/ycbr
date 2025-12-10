// File: `tanstack-ui/src/inspectionevents/BookingDialog.test.tsx`
import React from "react";
import { render, screen, fireEvent, waitFor, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@/auth/useUser", () => {
  const { vi: _vi } = require("vitest");
  return {
    useUser: _vi.fn(),
  };
});

const mockedCreateBooking = vi.fn();

vi.mock("./inspectionevent", () => {
  return {
    useAddBoatBooking: () => ({ mutateAsync: mockedCreateBooking }),
  };
});

// Minimal MUI primitives to allow interaction in tests
vi.mock("@mui/material", () => {
  const React = require("react");
  return {
    Tooltip: ({ children }: any) => React.createElement("span", null, children),
    IconButton: ({ children, ...props }: any) => React.createElement("button", props, children),
    Dialog: ({ open, children }: any) => (open ? React.createElement("div", { "data-testid": "dialog" }, children) : null),
    DialogTitle: ({ children }: any) => React.createElement("h2", null, children),
    DialogContent: ({ children }: any) => React.createElement("div", null, children),
    DialogActions: ({ children }: any) => React.createElement("div", null, children),
    List: ({ children }: any) => React.createElement("ul", null, children),
    ListItemText: ({ primary, secondary }: any) =>
      React.createElement("li", null, React.createElement("strong", null, primary), ": ", React.createElement("span", null, secondary)),
    Button: ({ children, ...props }: any) => React.createElement("button", props, children),
    FormControl: ({ children }: any) => React.createElement("div", null, children),
    InputLabel: ({ children }: any) => React.createElement("label", null, children),
    Select: ({ children }: any) => React.createElement("div", null, children),
    MenuItem: ({ children, ...props }: any) => React.createElement("button", props, children),
  };
});

vi.mock("@mui/icons-material/SailingSharp", () => {
  const React = require("react");
  return () => React.createElement("span", { "data-testid": "sailing-icon" });
});

import { useUser } from "@/auth/useUser";
import BookingDialog from "./BookingDialog";

const mockedUseUser = useUser as unknown as vi.Mock;

describe("BookingDialog", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    cleanup();
    mockedCreateBooking.mockResolvedValue(undefined);
  });

  it("renders nothing when myBoats is undefined", () => {
    mockedUseUser.mockImplementation(() => ({ user: { name: "noone" }, myBoats: undefined }));
    const row = { original: { id: 1, day: new Date().toISOString(), starts: new Date().toISOString(), ends: new Date().toISOString(), place: "X" } } as any;
    const { container } = render(React.createElement(BookingDialog, { row }));
    // Icon button should not be present when myBoats is falsy
    expect(container.querySelector("button")).toBeNull();
  });

  it("opens dialog when icon button is clicked and shows details", async () => {
    mockedUseUser.mockImplementation(() => ({
      user: { name: "Alice" },
      myBoats: [{ id: 7, name: "Sea Breeze" }],
    }));
    const row = { original: { id: 2, day: "2025-11-30T00:00:00Z", starts: "2025-11-30T09:00:00Z", ends: "2025-11-30T10:00:00Z", place: "Harbor" } } as any;
    render(React.createElement(BookingDialog, { row }));

    // Icon button should exist
    const iconBtn = screen.getByRole("button");
    expect(iconBtn).toBeTruthy();

    fireEvent.click(iconBtn);

    // Dialog title should appear
    await waitFor(() => {
      expect(screen.getByText("Book Boat Inspection")).toBeTruthy();
    });

    // Boat name should appear as selectable MenuItem
    expect(screen.getByText("Sea Breeze")).toBeTruthy();
    // Details should show Boat Owner and Location
    expect(screen.getByText("Boat Owner")).toBeTruthy();
    expect(screen.getByText("Location")).toBeTruthy();
    expect(screen.getByText("Harbor")).toBeTruthy();
  });

  it("selects a boat and calls createBooking with correct payload when Book clicked", async () => {
    mockedUseUser.mockImplementation(() => ({
      user: { name: "Bob" },
      myBoats: [
        { id: 7, name: "Sea Breeze" },
        { id: 8, name: "Wave Rider" },
      ],
    }));
    const row = { original: { id: 42, day: "2025-11-30T00:00:00Z", starts: "2025-11-30T09:00:00Z", ends: "2025-11-30T10:00:00Z", place: "Harbor" } } as any;
    render(React.createElement(BookingDialog, { row }));

    // Open dialog
    fireEvent.click(screen.getByRole("button"));

    await waitFor(() => screen.getByText("Book Boat Inspection"));

    // Select the first boat
    fireEvent.click(screen.getByText("Sea Breeze"));

    // Click Book
    const bookBtn = screen.getAllByRole("button").find((b) => b.textContent === "Book");
    expect(bookBtn).toBeTruthy();
    fireEvent.click(bookBtn as Element);

    await waitFor(() => {
      expect(mockedCreateBooking).toHaveBeenCalledTimes(1);
    });

    const expectedDto = {
      boatId: 7,
      message: "Sent by Bob",
      taken: false,
    };
    expect(mockedCreateBooking).toHaveBeenCalledWith({ id: 42, dto: expectedDto });
  });
});
