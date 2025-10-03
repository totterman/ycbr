import { keepPreviousData, queryOptions } from "@tanstack/react-query";
import axios, { AxiosError } from "axios";
import dayjs from "dayjs";

export type InspectionEventType = {
  id: number;
  place: string;
  day: string,
  from: string,
  to: string,
  inspectors: number;
  boats: number;
};

export class InspectionNotFoundError extends Error {}

export const i9eventsQueryOptions = queryOptions({
  queryKey: ["i9events"],
  queryFn: async () => {
    const data = await axios
      .get<InspectionEventType[]>("/bff/api/i9events")
      .then((res) => {
        return res.data ?? null;
      })
      .catch((err: AxiosError) => {
        throw err;
      });
    return data;
  },
  placeholderData: keepPreviousData,
});

export const i9eventQueryOptions = (inspectionId: string) =>
  queryOptions({
    queryKey: ["i9events", inspectionId],
    queryFn: async () => {
      const boat = await axios
        .get<InspectionEventType>(`/bff/api/i9events/${inspectionId}`)
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          if (err.status === 404) {
            throw new InspectionNotFoundError(
              `Inspection event with id ${inspectionId} not found!`
            );
          }
          throw err;
        });
      return boat;
    },
    // placeholderData: keepPreviousData,
  });
