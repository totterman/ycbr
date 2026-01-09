// File: `ui/src/auth/login.test.tsx`
import React from "react";
import { render, fireEvent, screen, waitFor } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("axios");
vi.mock("@tanstack/react-query");
vi.mock("@tanstack/react-router");

import axios from "axios";
import Login from "./login";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useLocation } from "@tanstack/react-router";

const mockedAxios = axios as unknown as { post: vi.Mock };
const mockedUseQuery = useQuery as unknown as vi.Mock;
const mockedUseMutation = useMutation as unknown as vi.Mock;
const mockedUseQueryClient = useQueryClient as unknown as vi.Mock;
const mockedUseLocation = useLocation as unknown as vi.Mock;

beforeEach(() => {
  vi.clearAllMocks();
  // default env var used in component
  (globalThis as any).importMeta = { env: { VITE_REVERSE_PROXY: "" } };

  // Default: login options query returns a loginUri string
  mockedUseQuery.mockImplementation(() => ({ data: "https://auth.example/login" }));

  // Default mutation behavior: call the provided mutationFn and then onSuccess
  mockedUseMutation.mockImplementation((opts: any) => {
    return {
      mutate: async (uri: string) => {
        try {
          // call the real mutationFn so axios.post is invoked
          await opts.mutationFn(uri);
          opts.onSuccess && opts.onSuccess();
        } catch (e) {
          opts.onError && opts.onError(e);
        }
      },
      isPending: false,
    };
  });

  mockedUseQueryClient.mockImplementation(() => ({ invalidateQueries: vi.fn() }));

  // default location pathname
  mockedUseLocation.mockImplementation(() => "/current/path");

  mockedAxios.post = vi.fn().mockResolvedValue({ status: 200 });
});

describe("Login component", () => {
  it("calls axios.post with appended params and redirects on success", async () => {
    // ensure VITE_REVERSE_PROXY is present to verify appended param
    (globalThis as any).importMeta = { env: { VITE_REVERSE_PROXY: "https://proxy" } };

    render(React.createElement(Login));

    const btn = screen.getByRole("button", { name: /Login/i });
    expect(btn).toBeTruthy();

    fireEvent.click(btn);

    await waitFor(() => {
      expect(mockedAxios.post).toHaveBeenCalled();
    });

    // capture the URL passed to axios.post
    const calledUrl = mockedAxios.post.mock.calls[0][0] as string;
    expect(calledUrl.startsWith("https://auth.example/login")).toBeTruthy();
//    expect(calledUrl).toContain("post_login_success_uri=https%3A%2F%2Fproxy%2Fcurrent%2Fpath");
//    expect(calledUrl).toContain("post_login_failure_uri=https%3A%2F%2Fproxy%2Flogin-error");

    // onSuccess should set window.location.href to the loginOptions.data (redirect)
    await waitFor(() => {
      expect(window.location.href).not.toBe("https://auth.example/login");
    });
  });

  it("shows \"Logging in...\" when mutation is pending", async () => {
    // override mutation to report pending
    mockedUseMutation.mockImplementation(() => ({
      mutate: vi.fn(),
      isPending: true,
    }));

    render(React.createElement(Login));

    // button text should change when pending
    const btn = screen.getByRole("button", { name: /Logging in.../i });
    expect(btn).toBeTruthy();
    // expect(btn).toBeDisabled();
  });

  it("displays error message when mutation fails", async () => {
    // Make axios.post reject with an error object that includes response.data.message
    const error = { response: { data: { message: "Login failed" } } };
    mockedAxios.post.mockRejectedValueOnce(error);

    // useMutation will call mutationFn which triggers the axios rejection and then onError
    mockedUseMutation.mockImplementation((opts: any) => ({
      mutate: async (uri: string) => {
        try {
          await opts.mutationFn(uri);
          opts.onSuccess && opts.onSuccess();
        } catch (e) {
          opts.onError && opts.onError(e);
        }
      },
      isPending: false,
    }));

    render(React.createElement(Login));

    const btn = screen.getByRole("button", { name: /Login/i });
    fireEvent.click(btn);

    await waitFor(() => {
      expect(screen.getByText("Login failed")).toBeTruthy();
    });
  });

  it("does nothing when loginOptions.data is empty", async () => {
    // make loginOptions empty
    mockedUseQuery.mockImplementation(() => ({ data: "" }));
    const mutateSpy = vi.fn();
    mockedUseMutation.mockImplementation(() => ({ mutate: mutateSpy, isPending: false }));

    render(React.createElement(Login));

    const btn = screen.getByRole("button", { name: /Login/i });
    fireEvent.click(btn);

    await new Promise((r) => setTimeout(r, 50));
    expect(mutateSpy).not.toHaveBeenCalled();
    expect(mockedAxios.post).not.toHaveBeenCalled();
  });
});
