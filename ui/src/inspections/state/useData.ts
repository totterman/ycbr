import { create } from "zustand";
import { InspectionDto } from "../inspection";

export type DataState = {
    data: InspectionDto;
    setData: (dto: InspectionDto) => void;
}

export const useData = create<DataState>((set) => ({
    data: initialDto,
    setData: (dto) => set({ data: dto }),
}))

const initialDto: InspectionDto = {
        timestamp: "",
        inspection: {
            hullData: {
                hull: false,
                openings: false,
                material: false,
                keel_rudder: false,
                steering: false,
                drive_shaft_prop: false,
                throughulls: false,
                fall_protection: false,
                heavy_objects: false,
                fresh_water: false,
                lowest_leak: 0
            },
            rigData: {
                rig: false,
                sails: false,
                storm_sails: false,
                reefing: false
            },
            engineData: {
                installation: false,
                controls: false,
                fuel_system: false,
                cooling: false,
                strainer: false,
                electrical: false,
                separate_batteries: false,
                shore_power: false,
                aggregate: false,
                reserve: false
            },
            equipmentData: {
                markings: false,
                anchors: false,
                sea_anchor: false,
                lines: false,
                tools: false,
                paddel: false,
                hook: false,
                resque_line: false,
                fenders: false,
                ladders: false,
                defroster: false,
                toilet: false,
                gas_system: false,
                stove: false,
                flag: false
            },
            navigationData: {
                lights: false,
                dayshapes: false,
                horn: false,
                reflector: false,
                compass: false,
                bearing: false,
                log: false,
                charts: false,
                radio: false,
                satnav: false,
                radar: false,
                spotlight: false,
                vhf: false,
                hand_vhf: false,
                documents: false
            },
            safetyData: {
                buoyancy: false,
                harness: false,
                lifebuoy: false,
                signals_a: false,
                signals_b: false,
                fixed_handpump: false,
                electric_pump: false,
                hand_extinguisher: false,
                fire_blanket: false,
                plugs: false,
                flashlight: false,
                firstaid: false,
                spare_steering: false,
                emergency_tools: false,
                reserves: false,
                liferaft: false,
                detector: false
            }
        },
        remarks: [],
        inspectionId: "",
        completed: "",
        inspectorName: "",
        eventId: "",
        boatId: "",
        inspectionClass: ""
    };
