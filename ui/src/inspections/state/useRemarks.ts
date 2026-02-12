import { Remark } from "../inspection";
import { create } from "zustand";

export type RemarksState = {
    remarks: Remark[];
    init: (seed: Remark[]) => void;
    addRemark: (item: Remark) => void;
    clearRemark: (id: number) => void;
}

export const useRemarks = create<RemarksState>((set) => ({
    remarks: [],
    init: (seed) => set(() => ({ remarks: seed })),
    addRemark: (item) => set((state) => ({ remarks: [...state.remarks, item] })),
    clearRemark: (id) => set((state) => ({ remarks: state.remarks?.filter((r) => r.id !== id)})),
}))