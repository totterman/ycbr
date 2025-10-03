import { keepPreviousData, queryOptions } from "@tanstack/react-query";
import axios, { AxiosError } from "axios";

export type BoatType = {
  id: number;
  //  id: string;
  name: string;
  sign: string;
  make: string;
  model: string;
  loa: number; // Length overall in meters
  draft: number; // Draft in meters
  beam: number; // Beam in meters
  deplacement: number; // Displacement in kilograms
  owner: string;
};

export class BoatNotFoundError extends Error {}

export const boatsQueryOptions = queryOptions({
  queryKey: ["boats"],
  queryFn: async () => {
    const data = await axios
      .get<BoatType[]>("/bff/api/boats")
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

export const boatQueryOptions = (boatId: string) => queryOptions({
  queryKey: ["boats", boatId],
  queryFn: async () => {
    const boat = await axios
      .get<BoatType>(`/bff/api/boats/${boatId}`)
      .then((res) => {
        return res.data ?? null;
      })
      .catch((err: AxiosError) => {
        if (err.status === 404) {
        throw new BoatNotFoundError(`Boat with id ${boatId} not found!`)
      }
        throw err;
      });
    return boat;
  },
  // placeholderData: keepPreviousData,
});
