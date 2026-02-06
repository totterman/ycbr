import { createContext } from "react";

export interface Category {
    kind: string;
    inspectionClass: string;
}

export const CategoryContext = createContext<Category>({ kind: 'S', inspectionClass: '0'});

export function isMotorboat(category: Category) {
    return category.kind === "M";
  }

export function isSailboat(category: Category) {
    return category.kind === "S";
  }

export function isOfClass(category: Category, iclasses: string) {
    return iclasses.includes(category.inspectionClass);
  }

