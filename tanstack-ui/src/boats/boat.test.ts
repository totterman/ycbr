// File: `tanstack-ui/src/boats/boat.spec.ts`
import { describe, it, expect, beforeEach, vi } from "vitest";
import axios from "axios";
import {
  boatQueryOptions,
  boatsQueryOptions,
  validateBoat,
  BoatNotFoundError,
  BoatType,
} from "./boat";

vi.mock("axios");

const mockedAxios = axios as unknown as {
  get: ReturnType<typeof vi.fn>;
  post?: ReturnType<typeof vi.fn>;
  put?: ReturnType<typeof vi.fn>;
  delete?: ReturnType<typeof vi.fn>;
};

const baseBoat = (overrides?: Partial<BoatType>): BoatType => ({
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

describe("boat utilities", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("validateBoat reports errors when name and owner are missing", () => {
    const boat = baseBoat({ name: "", owner: "" });
    const result = validateBoat(boat);
    expect(result).toEqual({
      name: "Boat Name is Required",
      owner: "Boat Owner is Required",
    });
  });

  it("validateBoat returns no errors when required fields present", () => {
    const boat = baseBoat({ name: "A", owner: "B" });
    const result = validateBoat(boat);
    expect(result).toEqual({ name: "", owner: "" });
  });

  it("boatQueryOptions.queryFn returns boat on successful axios.get", async () => {
    const boat = baseBoat();
    mockedAxios.get = vi.fn().mockResolvedValueOnce({ data: boat });

    const q = boatQueryOptions("1");
    const result = await q.queryFn();
    expect(mockedAxios.get).toHaveBeenCalledWith("/bff/api/boats/1");
    expect(result).toEqual(boat);
  });

  it("boatQueryOptions.queryFn throws BoatNotFoundError when axios.get rejects with status 404", async () => {
    // The implementation checks `err.status === 404`
    mockedAxios.get = vi.fn().mockRejectedValueOnce({ status: 404 });

    const q = boatQueryOptions("42");
    await expect(q.queryFn()).rejects.toBeInstanceOf(BoatNotFoundError);
  });

  it("boatsQueryOptions.queryFn returns array of boats on success", async () => {
    const boats = [baseBoat({ id: 1 }), baseBoat({ id: 2 })];
    mockedAxios.get = vi.fn().mockResolvedValueOnce({ data: boats });

    const q = boatsQueryOptions;
    // boatsQueryOptions is the options object itself
    const result = await q.queryFn();
    expect(mockedAxios.get).toHaveBeenCalledWith("/bff/api/boats");
    expect(result).toEqual(boats);
  });
});
