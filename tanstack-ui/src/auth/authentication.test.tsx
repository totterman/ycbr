// File: `tanstack-ui/src/auth/authentication.spec.tsx`
import React from "react";
import { render, screen } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";
import Authentication from "./authentication";

// Mock the default exported Login and Logout components to render identifiable text
vi.mock("./login", () => {
  const React = require("react");
  return {
    default: () => React.createElement("div", { "data-testid": "mock-login" }, "MockLogin"),
  };
});
vi.mock("./logout", () => {
  const React = require("react");
  return {
    default: () => React.createElement("div", { "data-testid": "mock-logout" }, "MockLogout"),
  };
});

// Mock the named export useUser
vi.mock("./useUser", () => ({
  useUser: vi.fn(),
}));

import { useUser } from "./useUser";
const mockedUseUser = useUser as unknown as vi.Mock;

describe("Authentication", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("shows loading state when isLoading is true", () => {
    mockedUseUser.mockReturnValue({ user: { isAuthenticated: false }, isLoading: true });
    render(React.createElement(Authentication));
    expect(screen.getByText("Loading...")).toBeTruthy();
  });

  it("renders Login when user is not authenticated", () => {
    mockedUseUser.mockReturnValue({ user: { isAuthenticated: false }, isLoading: false });
    render(React.createElement(Authentication));
    expect(screen.queryByTestId("mock-login")).toBeTruthy();
    expect(screen.queryByTestId("mock-logout")).toBeNull();
  });

  it("renders Logout when user is authenticated", () => {
    mockedUseUser.mockReturnValue({ user: { isAuthenticated: true }, isLoading: false });
    render(React.createElement(Authentication));
    expect(screen.queryByTestId("mock-logout")).toBeTruthy();
    expect(screen.queryByTestId("mock-login")).toBeNull();
  });
});
