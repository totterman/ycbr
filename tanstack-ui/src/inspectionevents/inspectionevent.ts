import {
  keepPreviousData,
  queryOptions,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import axios, { AxiosError } from "axios";
import dayjs from "dayjs";

export interface I9EventDto {
  i9eventId: string;
  place: string;
  day: string;
  starts: string;
  ends: string;
  inspectors: number;
  boats: number;
}

export interface NewI9EventDto {
  place: string;
  day: string;
  starts: string;
  ends: string;
}

export interface BoatBookingDto {
  boatId: string;
  message: string;
  taken: boolean;
}

export interface InspectorDto {
  inspectorName: string;
  message: string;
}

export interface CompleteEventDto {
  i9eventId: string;
  place: string;
  day: string;
  starts: string;
  ends: string;
  inspectors: InspectorDto[];
  boats: BoatBookingDto[];
}

export class InspectionEventNotFoundError extends Error {}

export const i9eventsQueryOptions = queryOptions({
  queryKey: ["i9events"],
  queryFn: async () => {
    const data = await axios
      .get<I9EventDto[]>("/bff/api/i9events")
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

export const i9eventQueryOptions = (i9eventId: string) =>
  queryOptions({
    queryKey: ["i9events", i9eventId],
    queryFn: async () => {
      const event = await axios
        .get<I9EventDto>(`/bff/api/i9events/${i9eventId}`)
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err);
          if (err.status === 404) {
            throw new InspectionEventNotFoundError(
              `Inspection event with id ${i9eventId} not found!`
            );
          }
          throw err;
        });
      if (event.starts.length < 6) {
        event.starts = event.day + 'T' + event.starts;
      }
      if (event.ends.length < 6) {
        event.ends = event.day + 'T' + event.ends;
      }
      return event;
    },
    // placeholderData: keepPreviousData,
  });

export function useGetI9Event(i9eventId: string) {
  const i9eventQuery = useQuery(i9eventQueryOptions(i9eventId));
  return i9eventQuery.data;
}

export function useCreateI9Event() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (i9Event: NewI9EventDto) => {
      console.log("New i9Event: ", i9Event);
      const response = await axios
        .post<I9EventDto>("/bff/api/i9events", i9Event, {
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
    /*
    onMutate: (newI9Event: NewI9EventDto) => {
      queryClient.setQueryData(
        ["i9events"],
        (prevEvents: any) =>
          [
            ...prevEvents,
            {
              ...newI9Event,
              id: Math.floor(Math.random() * 100),
            },
          ] as I9EventDto[]
      );
    },
    */
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

interface BookingProps {
  id: string;
  dto: BoatBookingDto;
}

export function useAddBoatBooking() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dto }: BookingProps) => {
      console.log("dto: ", dto);
      const response = await axios
        .post<BoatBookingDto>("/bff/api/i9events/" + id + "/boats", dto, {
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
            console.log('Boat', dto.boatId, 'is already booked for event', id);
          }
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

export function useMarkBoatBooking() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dto }: BookingProps) => {
      console.log("dto: ", dto);
      const response = await axios
        .post<BoatBookingDto>("/bff/api/i9events/" + id + "/mark", dto, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err.message);
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

export function useUnmarkBoatBooking() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dto }: BookingProps) => {
      console.log("dto: ", dto);
      const response = await axios
        .post<BoatBookingDto>("/bff/api/i9events/" + id + "/unmark", dto, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err.message);
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

interface InspectorProps {
  id: string;
  dto: InspectorDto;
}

export function useAddInspector() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, dto }: InspectorProps) => {
      console.log("dto: ", dto);
      const response = await axios
        .post<I9EventDto>("/bff/api/i9events/" + id + "/inspectors", dto, {
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
            console.log('Inspector', dto.inspectorName, 'has already registered for event', id);
          }
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch users after mutation, disabled for demo
  });
}

export function useRemoveInspector() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({ id, name }: { id: string, name: string }) => {
      console.log("name: ", name);
      const response = await axios
        .delete<I9EventDto>("/bff/api/i9events/" + id + "/inspectors", { params: {
        name: name,
      } })
        .then((res) => {
          return res.data ?? null;
        })
        .catch((err: AxiosError) => {
          console.log("Axios err: ", err.message);
        });
      return response;
    },
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["i9events"] }), //refetch events after mutation, disabled for demo
  });
}

export const completeQueryOptions = queryOptions({
  queryKey: ["i9events", "all"],
  queryFn: async () => {
    const a = dayjs();
    const data = await axios
      .get<CompleteEventDto[]>("/bff/api/i9events/all")
      .then((res) => {
        return res.data ?? null;
      })
      .catch((err: AxiosError) => {
        console.log("Axios err: ", err);
        throw err;
      });
    return data;
  },
  placeholderData: keepPreviousData,
});

const validateRequired = (value: string) => !!value.length;
const validateMaxSize = (value: string, maxSize: number) => !!value.length && value.length < maxSize;

export function validateEvent(event: I9EventDto | NewI9EventDto, content: any) {
  console.log("validateEvent(", event, ")");
  return {
    place:
      event.place !== undefined && validateMaxSize(event.place, 50)
        ? ""
        : content.location_required,
    day:
      event.day !== undefined && validateRequired(event.day)
        ? ""
        : content.date_required,
    starts:
      event.starts !== undefined && validateRequired(event.starts)
        ? ""
        : content.start_time_required,
    ends:
      event.ends !== undefined && validateRequired(event.ends)
        ? event.ends > event.starts
          ? ""
          : content.start_before_end
        : content.end_time_required,
  };
}
