import {
  MaterialReactTable,
  MRT_ColumnDef,
  useMaterialReactTable,
} from "material-react-table";
import { useMemo } from "react";
import { boatsQueryOptions, BoatType } from "./boat";
import { useSuspenseQuery } from "@tanstack/react-query";

export default function BoatsPage() {
  const columns = useMemo<MRT_ColumnDef<BoatType>[]>(
    () => [
      {
        accessorKey: "name",
        header: "Boat Name",
      },
      {
        accessorKey: "sign",
        header: "Boat Sign",
      },
      {
        accessorKey: "owner",
        header: "Owner",
      },
      {
        accessorKey: "make",
        header: "Make",
      },
      {
        accessorKey: "model",
        header: "Model",
      },
      {
        accessorKey: "loa",
        header: "LOA",
      },
      {
        accessorKey: "beam",
        header: "Beam",
      },
      {
        accessorKey: "draft",
        header: "Draft",
      },
      {
        accessorKey: "deplacement",
        header: "Deplacement",
      },
    ],
    []
  );

  const boatsQuery = useSuspenseQuery(boatsQueryOptions);
  const boats = boatsQuery.data;

  const table = useMaterialReactTable({
    columns,
    //  data,
    data: boats,
    getRowId: (row) => {
      return row.id.toString();
    },
    /*
    muiTableBodyRowProps: ({ row }) => ({
      onClick: () => {
        const id = `${row.id}`;
        goToBoat(id);
      },
      sx: {
        cursor: "zoom-in",
      },
    }),
    */
  });

  return <MaterialReactTable table={table} />;
}
