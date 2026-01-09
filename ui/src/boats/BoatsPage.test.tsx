// File: `ui/src/boats/BoatsPage.spec.tsx`
import { describe, it, expect, beforeEach, vi } from "vitest";
import React from "react";
import { render, fireEvent, screen } from "@testing-library/react";
import BoatsPage from "./BoatsPage";

// Mock material-react-table to make useMaterialReactTable return a controllable table object
vi.mock("material-react-table", () => {
  const React = require("react");
  let lastTable: any = null;
  return {
    useMaterialReactTable: (opts: any) => {
      const table = {
        ...opts,
        setCreatingRow: vi.fn(),
        setEditingRow: vi.fn(),
      };
      lastTable = table;
      return table;
    },
    MaterialReactTable: ({ table }: any) =>
      React.createElement(
        "div",
        {},
        // render top toolbar custom actions if provided
        table.renderTopToolbarCustomActions
          ? table.renderTopToolbarCustomActions({ table })
          : null,
        // render a detail placeholder using a sample row so we can assert the detail rendering
        React.createElement(
          "div",
          { "data-testid": "detail-panel" },
          table.renderDetailPanel
            ? table.renderDetailPanel({ row: { original: { id: 1 } } })
            : null
        )
      ),
    // helper to access last created table in tests
    __getLastTable: () => lastTable,
  };
});

// Mock boat hooks and helpers used by BoatsPage
vi.mock("./boat", () => {
  return {
    boatsQueryOptions: {},
    boatQueryOptions: (id: any) => ({ id }),
    validateBoat: vi.fn().mockReturnValue({}),
    useCreateBoat: () => ({ mutateAsync: vi.fn(), isPending: false }),
    useUpdateBoat: () => ({ mutateAsync: vi.fn(), isPending: false }),
    useDeleteBoat: () => ({ mutateAsync: vi.fn(), isPending: false }),
    BoatNotFoundError: class {},
  };
});

// Mock react-query suspense hook
vi.mock("@tanstack/react-query", () => ({
  useSuspenseQuery: vi.fn(),
}));

// Mock auth hook to return a staff user by default
vi.mock("@/auth/useUser", () => ({
  useUser: () => ({ user: { name: "TestUser", hasAnyRole: (r: string) => r === "staff" } }),
}));

import * as mrt from "material-react-table";
import { useSuspenseQuery } from "@tanstack/react-query";
import { boatsQueryOptions } from "./boat";

const mockedUseSuspenseQuery = useSuspenseQuery as unknown as vi.Mock;

const baseBoat = (overrides?: Partial<any>) => ({
  id: 1,
  name: "Test",
  sign: "S1",
  make: "Make",
  model: "Model",
  loa: 1,
  draft: 1,
  beam: 1,
  deplacement: 1,
  owner: "Owner",
  engines: "Engine",
  year: "2020",
  ...(overrides ?? {}),
});

describe("BoatsPage", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    // Default behavior: boats list and per-boat detail
    mockedUseSuspenseQuery.mockImplementation((opts: any) => {
      // if argument looks like a per-boat options (we mocked boatQueryOptions to return { id })
      if (opts && typeof opts === "object" && "id" in opts) {
        return { data: baseBoat({ id: opts.id }) };
      }
      // otherwise return boats list
      return { data: [baseBoat({ id: 1 }), baseBoat({ id: 2 })] };
    });
  });

  it("renders Create New Boat button for staff and clicking it calls table.setCreatingRow(true)", async () => {
    render(React.createElement(BoatsPage));
    // button is rendered by the mocked MaterialReactTable top toolbar action
    const btn = await screen.findByText("Create New Boat");
    expect(btn).toBeTruthy();

    fireEvent.click(btn);

    const lastTable = (mrt as any).__getLastTable();
    expect(lastTable).toBeTruthy();
    expect(lastTable.setCreatingRow).toHaveBeenCalledWith(true);
  });

  it("detail panel renders engine and year from per-boat query", async () => {
    render(React.createElement(BoatsPage));
    // the mocked MaterialReactTable renders the detail panel into data-testid "detail-panel"
    const detail = await screen.findByTestId("detail-panel");
    expect(detail).toBeTruthy();
    // The BoatsPage DetailPanel shows 'Engine:' and 'Year:' labels with values
    expect(detail.textContent).toContain("Engine:");
    expect(detail.textContent).toContain("Year:");
    expect(detail.textContent).toContain("Engine"); // value from baseBoat
    expect(detail.textContent).toContain("2020");
  });
});
