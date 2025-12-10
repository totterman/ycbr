// File: `tanstack-ui/src/inspectionevents/InspectionEventPage.test.tsx`
import React from "react";
import { render, screen, fireEvent, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@/auth/useUser", () => {
  const _vi = require("vitest");
  const mockUseUser = _vi.fn();
  return {
    useUser: mockUseUser,
  };
});

const mockedCreate = vi.fn();

vi.mock("./inspectionevent", () => {
  return {
    // validateEvent returns an error object when place === "BAD"
    validateEvent: (values: any) => {
      if (values?.place === "BAD") {
        return { place: "Location is Required", day: "", starts: "", ends: "" };
      }
      return { place: "", day: "", starts: "", ends: "" };
    },
    useCreateI9Event: () => ({ mutateAsync: mockedCreate, isPending: false }),
    // export i9eventsQueryOptions just as a token (we mock useSuspenseQuery below)
    i9eventsQueryOptions: {},
  };
});

vi.mock("@tanstack/react-query", () => {
  // Provide events data used as table data
  return {
    useSuspenseQuery: () => ({
      data: [
        {
          id: 1,
          place: "Harbor",
          day: "2025-11-30T00:00:00",
          starts: "2025-11-30T09:00:00",
          ends: "2025-11-30T10:00:00",
          inspectors: 0,
          boats: 0,
        },
      ],
    }),
  };
});

vi.mock("material-react-table", () => {
  const React = require("react");
  // Simple mock that renders toolbar/row actions and two buttons to trigger onCreatingRowSave
  return {
    MaterialReactTable: (props: any) =>
      React.createElement(
        "div",
        null,
        // render top toolbar custom actions if provided
        props.renderTopToolbarCustomActions ? props.renderTopToolbarCustomActions({ table: {} }) : null,
        // render row actions for a fake row if provided
        props.renderRowActions ? props.renderRowActions({ row: { id: "r1" }, table: {} }) : null,
        // Button to simulate saving an invalid new row (validation should fail)
        React.createElement(
          "button",
          {
            onClick: () =>
              props.onCreatingRowSave &&
              props.onCreatingRowSave({
                values: {
                  place: "BAD",
                  day: "2025-11-30T00:00:00",
                  starts: "2025-11-30T09:00:00",
                  ends: "2025-11-30T08:00:00",
                },
                table: { setCreatingRow: () => {} },
              }),
          },
          "Create Save Invalid",
        ),
        // Button to simulate saving a valid new row (should call create)
        React.createElement(
          "button",
          {
            onClick: () =>
              props.onCreatingRowSave &&
              props.onCreatingRowSave({
                values: {
                  place: "Pier",
                  day: "2025-11-30T00:00:00",
                  starts: "2025-11-30T09:00:00",
                  ends: "2025-11-30T10:00:00",
                },
                table: { setCreatingRow: () => {} },
              }),
          },
          "Create Save Valid",
        ),
      ),
  };
});

import { useUser } from "@/auth/useUser";
import { InspectionEventPage } from "./InspectionEventPage";

const mockedUseUser = useUser as unknown as vi.Mock;

describe("InspectionEventPage", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    cleanup();
  });

  it("shows Create New Inspection Event button when user has staff role", () => {
    mockedUseUser.mockImplementation(() => ({
      user: { hasAnyRole: (role: string) => role === "staff" },
      isError: false,
    }));

    render(React.createElement(InspectionEventPage));
    // the page renders the custom top toolbar button when user.hasAnyRole('staff') is true
    expect(screen.getByText("Create New Inspection Event")).toBeTruthy();
  });

  it("does not show Create button when user is not staff", () => {
    mockedUseUser.mockImplementation(() => ({
      user: { hasAnyRole: () => false },
      isError: false,
    }));

    render(React.createElement(InspectionEventPage));
    expect(screen.queryByText("Create New Inspection Event")).toBeNull();
  });

  it("does not call createI9Event when validation fails", () => {
    mockedUseUser.mockImplementation(() => ({
      user: { hasAnyRole: () => true }, // roles don't matter for this flow
      isError: false,
    }));

    render(React.createElement(InspectionEventPage));

    fireEvent.click(screen.getByText("Create Save Invalid"));
    expect(mockedCreate).not.toHaveBeenCalled();
  });

  it("calls createI9Event with normalized starts/ends when valid", async () => {
    mockedUseUser.mockImplementation(() => ({
      user: { hasAnyRole: () => true },
      isError: false,
    }));

    // make the mocked create resolve
    mockedCreate.mockResolvedValue(undefined);

    render(React.createElement(InspectionEventPage));

    fireEvent.click(screen.getByText("Create Save Valid"));

    // Expect create called once with normalized object:
    // component builds starts/ends as: day.split('T')[0] + 'T' + values.starts.split('T')[1]
    const expectedDayPrefix = "2025-11-30";
    const expected = {
      id: null,
      place: "Pier",
      day: "2025-11-30T00:00:00",
      starts: `${expectedDayPrefix}T09:00:00`,
      ends: `${expectedDayPrefix}T10:00:00`,
      inspectors: 0,
      boats: 0,
    };

    // assert called with the normalized event
    expect(mockedCreate).toHaveBeenCalledTimes(1);
    // first argument to mutateAsync should equal expected object
    expect(mockedCreate).toHaveBeenCalledWith(expected);
  });
});
