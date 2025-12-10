import {
  queryOptions,
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";
import axios, { AxiosError } from "axios";

export interface NewInspection {
  inspectorName: string;
  eventId: number;
  boatId: number;
}

export interface InspectionData {
  hullData: HullData;
  rigData: RigData;
  engineData: EngineData;
}

export interface HullData {
  hull: boolean;
  openings: boolean;
  material: boolean;
  keel_rudder: boolean;
  steering: boolean;
  drive_shaft_prop: boolean;
  throughulls: boolean;
  fall_protection: boolean;
  heavy_objects: boolean;
  fresh_water: boolean;
  lowest_leak: number;
}

export interface RigData {
  rig: boolean;
  sails: boolean;
  storm_sails: boolean;
  reefing: boolean;
}

export interface EngineData {
  installation: boolean,
  controls: boolean;
  fuel_system: boolean;
  cooling: boolean;
  strainer: boolean;
  separate_batteries: boolean;
  shore_power: boolean;
  aggregate: boolean;
}

export interface InspectionProps {
  data: InspectionDto;
}

export interface InspectionDto {
  id: number;
  timestamp: string;
  inspector: string;
  event: number;
  boat: number;
  inspection: InspectionData;
  completed: string;
}

export function useCreateInspection() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: createInspection,
    onSettled: () =>
      queryClient.invalidateQueries({ queryKey: ["inspections"] }), //refetch users after mutation, disabled for demo
  });
}

export const inspectionQueryOptions = (inspectionId: number) =>
  queryOptions({
    queryKey: ["inspections", inspectionId],
    queryFn: () => fetchInspection(inspectionId),
    // placeholderData: keepPreviousData,
  });

  export const inspectionsQueryOptions = (inspector: string) =>
  queryOptions({
    queryKey: ["inspections", inspector],
    queryFn: () => fetchMyInspections(inspector),
    // placeholderData: keepPreviousData,
  });

export const useUpdateInspection = (inspectionId: number) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: ['inspections', 'update', inspectionId],
    mutationFn: updateInspection,
    onSettled: () => queryClient.invalidateQueries({ queryKey: ["inspections", inspectionId] }),
  })
}

async function createInspection(dto: NewInspection) {
  const response = await axios
    .post<InspectionDto>("/bff/api/inspections", dto, {
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
}

async function fetchInspection(inspectionId: number) {
  return await axios
    .get<InspectionDto>(`/bff/api/inspections/${inspectionId}`)
    .then((res) => {
      return res.data ?? null;
    })
    .catch((err: AxiosError) => {
      console.log("Axios err: ", err);
      throw err;
    });
}

async function fetchMyInspections(inspector: string) {
  return await axios
    .get<InspectionDto[]>("/bff/api/inspections/inspector", { params: {
        name: inspector,
      } })
    .then((res) => {
      return res.data ?? null;
    })
    .catch((err: AxiosError) => {
      console.log("Axios err: ", err);
      throw err;
    });
}
    
async function updateInspection(dto: InspectionDto) {
  const id = dto.id;
  const response = await axios
    .put<InspectionDto>(`/bff/api/inspections/${id}`, dto, {
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
}
