import { keepPreviousData, queryOptions, useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import axios, { AxiosError } from "axios";

export interface BoatType {
  // id: number;
  boatId: string;
  name: string;
  sign: string;
  make: string;
  model: string;
  loa: number; // Length overall in meters
  draft: number; // Draft in meters
  beam: number; // Beam in meters
  deplacement: number; // Displacement in kilograms
  owner: string;
  engines: string;
  year: string;
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

export function useGetBoat(boatId: string) {
  const boatQuery = useQuery(boatQueryOptions(boatId));
  return boatQuery.data;
}

export function useCreateBoat() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (boat: BoatType) => {
      console.log("Creating new boat: ", boat);
      const response = await axios
        .post<BoatType>("/bff/api/boats", boat, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          throw err;
        });
      console.log("POST response: ", response);
      return response;
    },

    //client side optimistic update
    onMutate: (newBoatInfo: BoatType) => {
      queryClient.setQueryData(
        ["boats"],
        (prevBoats: any) =>
          [
            ...prevBoats,
            {
              ...newBoatInfo,
              boatId: (Math.random() + 1).toString(36).substring(7),
            },
          ] as BoatType[]
      );
    },

    // onSettled: () => queryClient.invalidateQueries({ queryKey: ['boats'] }), //refetch users after mutation, disabled for demo
  });
}

export function useUpdateBoat() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (boat: BoatType) => {
      console.log("Updating boat: ", boat);
      const response = await axios
        .put<BoatType>("/bff/api/boats/" + boat.boatId, boat, {
          headers: { "Content-Type": "application/json" },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          throw err;
        });
      console.log("PUT response: ", response);
      return response;
    },

    //client side optimistic update
    onMutate: (newBoatInfo: BoatType) => {
      queryClient.setQueryData(["boats"], (prevBoats: any) =>
        prevBoats?.map((prevBoat: BoatType) =>
          prevBoat.boatId === newBoatInfo.boatId ? newBoatInfo : prevBoat
        )
      );
    },
    // onSettled: () => queryClient.invalidateQueries({ queryKey: ['users'] }), //refetch users after mutation, disabled for demo
  });
}

export function useDeleteBoat() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (boatId: string) => {
      console.log("Deleting boat with ID: ", boatId);
      const response = await axios
        .delete<BoatType>("/bff/api/boats/" + boatId, {
          headers: { "Content-Type": "application/json" },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          throw err;
        });
      console.log("DELETE response: ", response);
      return response;
    },

    //client side optimistic update
    onMutate: (boatId: string) => {
      queryClient.setQueryData(["boats"], (prevBoats: any) =>
        prevBoats?.filter((boat: BoatType) => boat.boatId !== boatId)
      );
    },

    // onSettled: () => queryClient.invalidateQueries({ queryKey: ['users'] }), //refetch users after mutation, disabled for demo
  });
}

const validateRequired = (value: string) => !!value.length;

export function validateBoat(boat: BoatType, content: any) {
  return {
    name: !validateRequired(boat.name) ? content.nameRequired : "",
    owner: !validateRequired(boat.owner) ? content.ownerRequired : "",
    //    email: !validateEmail(boat.email) ? 'Incorrect Email Format' : '',
  };
}
