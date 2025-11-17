import {
  keepPreviousData,
  queryOptions,
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";
import axios, { AxiosError } from "axios";
import dayjs from "dayjs";

export interface InspectionDao {
  id: number | null;
  place: string;
  day: string;
  starts: string;
  ends: string;
  inspectors: number;
  boats: number;
}

export interface BoatBookingDao {
  boatName: string;
  message: string;
}

export interface InspectorDao {
  inspectorName: string;
  message: string;
}

export class InspectionNotFoundError extends Error {}

export const i9eventsQueryOptions = queryOptions({
  queryKey: ["i9events"],
  queryFn: async () => {
    const a = dayjs();
    const data = await axios
      .get<InspectionDao[]>("/bff/api/i9events")
      .then((res) => {
        return res.data ?? null;
      })
      .catch((err: AxiosError) => {
        console.log("Axios err: ", err);
        throw err;
      });
    data.forEach((d) => {
      if (d.starts.length < 6) {
        d.starts = d.day.concat(" ").concat(d.starts);
      }
      if (d.ends.length < 6) {
        d.ends = d.day.concat(" ").concat(d.ends);
      }
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
        .get<InspectionDao>(`/bff/api/i9events/${inspectionId}`)
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err);
          if (err.status === 404) {
            throw new InspectionNotFoundError(
              `Inspection event with id ${inspectionId} not found!`
            );
          }
          throw err;
        });
      if (boat.starts.length < 6) {
        boat.starts = boat.day + 'T' + boat.starts;
      }
      if (boat.ends.length < 6) {
        boat.ends = boat.day + 'T' + boat.ends;
      }
      return boat;
    },
    // placeholderData: keepPreviousData,
  });

export function useCreateI9Event() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (i9Event: InspectionDao) => {
      console.log("i9Event: ", i9Event);
      const response = await axios
        .post<InspectionDao>("/bff/api/i9events", i9Event, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err);
          throw err;
        });
      return response;
    },

    //client side optimistic update
    onMutate: (newI9Event: InspectionDao) => {
      queryClient.setQueryData(
        ["i9events"],
        (prevEvents: any) =>
          [
            ...prevEvents,
            {
              ...newI9Event,
              id: Math.floor(Math.random() * 100),
            },
          ] as InspectionDao[]
      );
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

type BookingProps = {
  id: number;
  dao: BoatBookingDao;
}

export function useAddBoatBooking() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dao }: BookingProps) => {
      console.log("dao: ", dao);
      const response = await axios
        .post<InspectionDao>("/bff/api/i9events/" + id + "/boats", dao, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err.message);
          if (err.status === 409) {
            console.log('Boat', dao.boatName, 'is already booked for event', id);
          }
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

type InspectorProps = {
  id: number;
  dao: InspectorDao;
}

export function useAddInspector() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dao }: InspectorProps) => {
      console.log("dao: ", dao);
      const response = await axios
        .post<InspectionDao>("/bff/api/i9events/" + id + "/inspectors", dao, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err.message);
          if (err.status === 409) {
            console.log('Inspector', dao.inspectorName, 'has already registered for event', id);
          }
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

const validateRequired = (value: string) => !!value.length;

export function validateEvent(event: InspectionDao) {
  console.log("validateEvent(", event, ")");
  return {
    place:
      event.place !== undefined && validateRequired(event.place)
        ? ""
        : "Location is Required",
    day:
      event.day !== undefined && validateRequired(event.day)
        ? ""
        : "Inspection Date is Required",
    starts:
      event.starts !== undefined && validateRequired(event.starts)
        ? ""
        : "Start Time is Required",
    ends:
      event.ends !== undefined && validateRequired(event.ends)
        ? event.ends > event.starts
          ? ""
          : "End Time must be after Start Time"
        : "End Time is Required",
  };
}
