import {
  queryOptions,
  useMutation,
  useQueryClient,
} from "@tanstack/react-query";
import axios, { AxiosError } from "axios";

export interface NewInspection {
  inspectorName: string;
  eventId: string;
  boatId: string;
}

export interface InspectionData {
  hullData: HullData;
  rigData: RigData;
  engineData: EngineData;
  equipmentData: EquipmentData;
  maritimeData: MaritimeData;
  safetyData: SafetyData;
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

export interface EquipmentData {
  markings: boolean;
  anchors: boolean;
  sea_anchor: boolean;
  lines: number;
  tools: boolean;
  paddel: boolean;
  hook: boolean;
  resque_line: boolean;
  fenders: boolean;
  ladders: boolean;
  defroster: boolean;
  toilet: boolean;
  gas_system: boolean;
  stove: boolean;
  flag: boolean;
}

export interface MaritimeData {
  lights: boolean;
  dayshapes: boolean;
  horn: boolean;
  reflector: boolean;
  compass: boolean;
  bearing: boolean;
  log: boolean;
  charts: boolean;
  radio: boolean;
  satnav: boolean;
  radar: boolean;
  spotlight: boolean;
  vhf: boolean;
  hand_vhf: boolean;
  documents: boolean;
}

export interface SafetyData {
  buoyancy: boolean;
  harness: number;
  lifebuoy: number;
  signals_a: boolean;
  signals_b: boolean;
  fixed_handpump: number;
  electric_pump: boolean;
  hand_extinguisher: number;
  fire_blanket: boolean;
  plugs: boolean;
  flashlight: number;
  firstaid: boolean;
  spare_steering: boolean;
  emergency_tools: boolean;
  reserves: boolean;
  liferaft: boolean;
  detector: boolean;
}


export interface InspectionProps {
  data: InspectionDto;
}

export interface InspectionDto {
  inspectionId: string;
  timestamp: string;
  inspector: string;
  eventId: string;
  boatId: string;
  inspection: InspectionData;
  completed: string;
}

export interface MyInspectionsDto {
  inspectionId: string;
  inspectorName: string;
  boatName: string;
  place: string;
  day: string;
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

export const inspectionQueryOptions = (inspectionId: string) =>
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

export const useUpdateInspection = (inspectionId: string) => {
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

async function fetchInspection(inspectionId: string) {
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
  const id = dto.inspectionId;
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
