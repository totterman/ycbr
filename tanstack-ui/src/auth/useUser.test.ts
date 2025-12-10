// File: `tanstack-ui/src/auth/useUser.test.ts`
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@tanstack/react-query", () => ({
  useQuery: vi.fn(),
}));

import { useQuery } from "@tanstack/react-query";
import { useUser, User } from "./useUser";

const mockedUseQuery = useQuery as unknown as vi.Mock;

describe("useUser hook", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("returns ANONYMOUS user when backend provides no username", () => {
    mockedUseQuery.mockImplementation((opts: any) => {
      const key = Array.isArray(opts.queryKey) ? opts.queryKey[0] : undefined;
      if (key === "user") {
        return {
          data: User.ANONYMOUS,
          isLoading: false,
          isFetching: false,
          isError: false,
          error: null,
        };
      }
      // boats / inspections should be disabled and thus return undefined data
      return { data: undefined, status: "idle", fetchStatus: "idle" };
    });

    const result = useUser();
    expect(result.user).toBe(User.ANONYMOUS);
    expect(result.isLoading).toBe(false);
    expect(result.myBoats).toBeUndefined();
    expect(result.myInspections).toBeUndefined();
  });

  it("returns user with roles and fetches boats and inspections when enabled", () => {
    const sampleBoats = [{ id: 1, name: "B1" }, { id: 2, name: "B2" }];
    const sampleInspections = [{ id: 10 }, { id: 11 }];

    mockedUseQuery.mockImplementation((opts: any) => {
      const key = Array.isArray(opts.queryKey) ? opts.queryKey[0] : undefined;
      if (key === "user") {
        // simulate selected User instance returned by the user query
        return {
          data: new User("john", "j@ex", "J", "D", ["boatowner", "inspector"]),
          isLoading: false,
          isFetching: false,
          isError: false,
          error: null,
        };
      }
      if (key === "boats") {
        // boats query should be enabled for a boatowner
        expect(opts.enabled).toBeTruthy();
        // also ensure queryKey contains the username
        expect(opts.queryKey[1]).toBe("john");
        return { data: sampleBoats, status: "success", fetchStatus: "idle" };
      }
      if (key === "inspections") {
        // inspections query should be enabled for an inspector
        expect(opts.enabled).toBeTruthy();
        expect(opts.queryKey[1]).toBe("john");
        return { data: sampleInspections, status: "success", fetchStatus: "idle" };
      }
      return { data: undefined };
    });

    const result = useUser();
    expect(result.user.name).toBe("john");
    expect(result.user.hasAnyRole("boatowner")).toBeTruthy();
    expect(result.myBoats).toEqual(sampleBoats);
    expect(result.myInspections).toEqual(sampleInspections);
  });

  it("provides a refetchInterval function that returns false when no exp and a positive delay when exp present", () => {
    // Capture the options passed for the user query to inspect refetchInterval
    mockedUseQuery.mockImplementation((opts: any) => {
      const key = Array.isArray(opts.queryKey) ? opts.queryKey[0] : undefined;
      if (key === "user") {
        return {
          data: new User("alice", "a@e", "A", "L", ["staff"]),
          isLoading: false,
          isFetching: false,
          isError: false,
          error: null,
          // include opts so we can access them via mock.calls
        };
      }
      return { data: undefined };
    });

    useUser();

    // obtain the original options object passed to useQuery for the user call
    const firstCallOpts = mockedUseQuery.mock.calls[0][0] as any;
    expect(typeof firstCallOpts.refetchInterval).toBe("function");

    // no exp => should return false
    const noExp = firstCallOpts.refetchInterval({ state: { data: {} } });
    expect(noExp).toBe(false);

    // exp shortly in the future -> compute to produce delay > 2000
    const futureMs = Date.now() + 5000; // 5s in future
    const futureExpSec = Math.ceil(futureMs / 1000);
    const delay = firstCallOpts.refetchInterval({ state: { data: { exp: futureExpSec } } });
    expect(typeof delay === "number" && delay > 2000).toBeTruthy();
  });

  it("logs an error when the user query is in error state", () => {
    const err = new Error("boom");
    const spy = vi.spyOn(console, "error").mockImplementation(() => {});

    mockedUseQuery.mockImplementation((opts: any) => {
      const key = Array.isArray(opts.queryKey) ? opts.queryKey[0] : undefined;
      if (key === "user") {
        return { data: undefined, isLoading: false, isFetching: false, isError: true, error: err };
      }
      return { data: undefined };
    });

    useUser();
    expect(spy).toHaveBeenCalledWith("Failed to fetch user data: ", err);
    spy.mockRestore();
  });
});
