// File: `ui/src/components/YcbrAppBar.test.tsx`
import React from "react";
import { render, screen, cleanup } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@/auth/useUser", () => {
  const { vi: _vi } = require("vitest");
  return {
    // export a mockable function
    useUser: _vi.fn(),
    // export a placeholder User (component only imports it but tests don't use it)
    User: {},
  };
});

// Mock the local CustomLink to render a simple anchor using `to` as `href`
vi.mock("./CustomLink", () => {
  const React = require("react");
  return {
    CustomLink: React.forwardRef(({ to, children, ...rest }: any, ref: any) =>
      React.createElement("a", { ref, href: to, ...rest }, children),
    ),
  };
});

// Mock the Authentication component to a simple visible element
vi.mock("@/auth/authentication", () => {
  const React = require("react");
  return {
    default: () => React.createElement("div", null, "Auth Component"),
  };
});

// Provide minimal mocks for the MUI primitives used by the app bar
vi.mock("@mui/material", () => {
  const React = require("react");
  return {
    AppBar: ({ children }: any) => React.createElement("div", { "data-testid": "appbar" }, children),
    Toolbar: ({ children }: any) => React.createElement("div", { "data-testid": "toolbar" }, children),
    Box: ({ children, ...props }: any) => React.createElement("div", props, children),
    IconButton: ({ children, ...props }: any) => React.createElement("button", props, children),
    // styled should accept a component and return a function that accepts styles and returns the original component
    styled: (Comp: any) => (_styles: any) =>
      React.forwardRef((props: any, ref: any) => React.createElement(Comp, { ref, ...props })),
    css: () => "", // no-op for template styling
  };
});

import { useUser } from "@/auth/useUser";
import YcbrAppBar from "./YcbrAppBar";

const mockedUseUser = useUser as unknown as vi.Mock;

describe("YcbrAppBar", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    cleanup();
  });

  it("shows loading state when useUser is loading", () => {
    mockedUseUser.mockImplementation(() => ({ user: { isAuthenticated: false, hasAnyRole: () => false }, isLoading: true }));
    render(React.createElement(YcbrAppBar));
    expect(screen.getByText("Loading...")).toBeTruthy();
  });

  it("renders base links and authentication when user is anonymous", () => {
    mockedUseUser.mockImplementation(() => ({ user: { isAuthenticated: false, hasAnyRole: () => false }, isLoading: false }));
    render(React.createElement(YcbrAppBar));

    // Always present links
    expect(screen.getByText("Home")).toBeTruthy();
    expect(screen.getByText("About")).toBeTruthy();

    // Boats should not be shown for anonymous
    expect(screen.queryByText("Boats")).toBeNull();

    // Role-based links should not appear
    expect(screen.queryByText("Inspection Events")).toBeNull();
    expect(screen.queryByText("Dispatch")).toBeNull();
    expect(screen.queryByText("Boat Inspections")).toBeNull();

    // Authentication component should be rendered
    expect(screen.getByText("Auth Component")).toBeTruthy();
  });

  it("renders Boats when user is authenticated", () => {
    mockedUseUser.mockImplementation(() => ({ user: { isAuthenticated: true, hasAnyRole: () => false }, isLoading: false }));
    render(React.createElement(YcbrAppBar));

    expect(screen.getByText("Home")).toBeTruthy();
    expect(screen.getByText("About")).toBeTruthy();
    expect(screen.getByText("Boats")).toBeTruthy();
  });

  it("renders role-based links when user has appropriate roles", () => {
    // Simulate a user that has boatowner, staff and inspector roles
    mockedUseUser.mockImplementation(() => ({
      user: {
        isAuthenticated: true,
        hasAnyRole: (...roles: string[]) => {
          // pretend user has all roles we care about
          const granted = new Set(["boatowner", "staff", "inspector"]);
          return roles.some((r) => granted.has(r));
        },
      },
      isLoading: false,
    }));

    render(React.createElement(YcbrAppBar));

    expect(screen.getByText("Boats")).toBeTruthy();
    expect(screen.getByText("Inspection Events")).toBeTruthy();
    expect(screen.getByText("Dispatch")).toBeTruthy();
    expect(screen.getByText("Boat Inspections")).toBeTruthy();
  });
});
