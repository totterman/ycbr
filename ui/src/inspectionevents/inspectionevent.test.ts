// File: `ui/src/inspectionevents/inspectionevent.test.ts`
import { describe, it, expect, beforeEach, vi } from "vitest";
import axiosDefault from "axios";

vi.mock("axios", () => {
  return {
    __esModule: true,
    default: {
      get: vi.fn(),
      post: vi.fn(),
    },
  };
});

import {
  i9eventsQueryOptions,
  i9eventQueryOptions,
  completeQueryOptions,
  validateEvent,
  InspectionEventNotFoundError,
} from "./inspectionevent";

const axios = axiosDefault as unknown as { get: vi.Mock; post: vi.Mock };

describe("inspectionevent module", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("i9eventsQueryOptions.queryFn normalizes short starts/ends by prepending day + ' '", async () => {
    const raw = [
      {
        id: 1,
        place: "Harbor",
        day: "2025-11-30",
        starts: "09:00", // length < 6 -> will be prefixed with "day "
        ends: "10:00",
        inspectors: 0,
        boats: 0,
      },
    ];
    axios.get.mockResolvedValue({ data: raw });

    const result = await i9eventsQueryOptions.queryFn();
    expect(Array.isArray(result)).toBe(true);
    expect(result[0].starts).toBe("2025-11-30 09:00");
    expect(result[0].ends).toBe("2025-11-30 10:00");
    expect(axios.get).toHaveBeenCalledWith("/bff/api/i9events");
  });

  it("i9eventsQueryOptions.queryFn rethrows network errors", async () => {
    axios.get.mockRejectedValue(new Error("network failure"));
    await expect(i9eventsQueryOptions.queryFn()).rejects.toThrow("network failure");
  });

  it("i9eventQueryOptions.queryFn normalizes short starts/ends by prepending day + 'T'", async () => {
    const ev = {
      id: 2,
      place: "Dock",
      day: "2025-12-01",
      starts: "08:30",
      ends: "09:30",
      inspectors: 1,
      boats: 2,
    };
    axios.get.mockResolvedValue({ data: ev });

    const fn = i9eventQueryOptions("2").queryFn;
    const result = await fn();
    expect(result.starts).toBe("2025-12-01T08:30");
    expect(result.ends).toBe("2025-12-01T09:30");
    expect(axios.get).toHaveBeenCalledWith("/bff/api/i9events/2");
  });

  it("i9eventQueryOptions.queryFn throws InspectionEventNotFoundError on 404", async () => {
    // mock rejection with a value that has status === 404 (code checks err.status)
    axios.get.mockRejectedValue({ status: 404, message: "not found" });
    const fn = i9eventQueryOptions("99").queryFn;
    await expect(fn()).rejects.toBeInstanceOf(InspectionEventNotFoundError);
  });

  it("completeQueryOptions.queryFn returns data from /bff/api/i9events/all", async () => {
    const all = [
      {
        id: 3,
        place: "Pier",
        day: "2025-11-30",
        starts: "07:00",
        ends: "08:00",
        inspectors: 0,
        boats: 0,
        inspectors: [],
        boats: [],
      },
    ];
    axios.get.mockResolvedValue({ data: all });
    const result = await completeQueryOptions.queryFn();
    expect(result).toBe(all);
    expect(axios.get).toHaveBeenCalledWith("/bff/api/i9events/all");
  });

  describe("validateEvent", () => {
    it("returns empty error strings for a valid event", () => {
      const ev = {
        id: null,
        place: "A",
        day: "2025-11-30",
        starts: "09:00",
        ends: "10:00",
        inspectors: 0,
        boats: 0,
      };
      const res = validateEvent(ev as any);
      expect(res.place).toBe("");
      expect(res.day).toBe("");
      expect(res.starts).toBe("");
      expect(res.ends).toBe("");
    });

    it("validates required fields", () => {
      const ev = {
        id: null,
        place: "",
        day: "",
        starts: "",
        ends: "",
        inspectors: 0,
        boats: 0,
      };
      const res = validateEvent(ev as any);
      expect(res.place).toBe("Location is Required");
      expect(res.day).toBe("Inspection Date is Required");
      expect(res.starts).toBe("Start Time is Required");
      expect(res.ends).toBe("End Time is Required");
    });

    it("returns error when end is not after start", () => {
      const ev = {
        id: null,
        place: "X",
        day: "2025-11-30",
        starts: "10:00",
        ends: "09:00",
        inspectors: 0,
        boats: 0,
      };
      const res = validateEvent(ev as any);
      expect(res.ends).toBe("End Time must be after Start Time");
    });
  });
});
