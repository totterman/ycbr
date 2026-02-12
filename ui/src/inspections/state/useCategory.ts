import { create } from "zustand";

export type CategoryState = {
    kind: string;
    inspectionClass: string;
    init: (k: string, i: string) => void;
    isMotorboat: boolean;
    isSailboat: boolean;
    cl1: boolean;
    cl2: boolean;
    cl3: boolean;
    cl4: boolean;
}

export const useCategory = create<CategoryState>((set) => ({
    kind: 'S',
    inspectionClass: '0',
    isMotorboat: false,
    isSailboat: true,
    cl1: false,
    cl2: false,
    cl3: false,
    cl4: false,
    init: (k, i) => set((state) => ({ 
        kind: k, 
        inspectionClass: i, 
        isMotorboat: state.kind === 'M', 
        isSailboat: state.kind === 'S', 
        cl1: state.inspectionClass === '1',
        cl2: state.inspectionClass === '2',
        cl3: state.inspectionClass === '3',
        cl4: state.inspectionClass === '4',
    })),
}))

export function isOfClass(iclasses: string): boolean {
    const { inspectionClass } = useCategory();
    return iclasses.includes(inspectionClass);
  }

