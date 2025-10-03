import { createFileRoute } from "@tanstack/react-router";
import { useMemo, useState } from "react";
import {
  MaterialReactTable,
  MRT_ColumnDef,
  useMaterialReactTable,
} from "material-react-table";
import { InspectionEventType } from "@/inspections/inspection";
import { events } from "@/inspections/static_inspectiondata";
import Box from "@mui/material/Box";
import { useUser } from "@/auth/useUser";
import RegisterDialog from "@/inspections/RegisterDialog";
import BookingDialog from "@/inspections/BookingDialog";

export const Route = createFileRoute("/inspect")({
  component: InspectionEventPage,
});


function InspectionEventPage() {
  const [validationErrors, setValidationErrors] = useState<
    Record<string, string | undefined>
  >({});
  const { user, isError } = useUser();
  const columns = useMemo<MRT_ColumnDef<InspectionEventType>[]>(
    () => [
      /*
      {
        id: "registerInspector",
        header: "Register as Inspector",
        columnDefType: "display", //turns off data column features like sorting, filtering, etc.
        enableColumnOrdering: true, //but you can turn back any of those features on if you want like this
        Cell: ({ row }) => <RegisterDialog row={row} />,
        muiTableBodyCellProps: {
          align: "center",
        },
      },
      */
      {
        accessorKey: "day",
        header: "Date",
        enableEditing: false,
        size: 150,
      },
      {
        accessorKey: "place",
        header: "Location",
        enableEditing: false,
        size: 150,
      },
      {
        accessorKey: "from",
        header: "Start time",
        enableEditing: false,
        size: 100,
      },
      {
        accessorKey: "to",
        header: "End time",
        enableEditing: false,
        size: 100,
      },
      {
        accessorKey: "inspectors",
        header: "Nr inspectors",
        enableEditing: false,
        enableColumnFilter: false,
        enableSorting: false,
        size: 100,
      },
      {
        accessorKey: "boats",
        header: "Nr boats",
        enableEditing: false,
        enableColumnFilter: false,
        enableSorting: false,
        size: 100,
      },
    ],
    []
  );

  const data = events;

  const [values, setValues] = useState<any>(() =>
    columns.reduce((acc, column) => {
      acc[column.accessorKey ?? ""] = "";
      return acc;
    }, {} as any)
  );

  const table = useMaterialReactTable({
    columns,
    data,
    getRowId: (row) => {
      return row.id.toString();
    },
    createDisplayMode: "modal",
    displayColumnDefOptions: { 'mrt-row-actions': { size: 300, muiTableBodyCellProps: { align: 'center' } } },
    enableRowActions: true,
    renderRowActions: ({ row, table }) => (
      <Box sx={{ display: 'flex', gap: '1rem' }}>
        {user.hasAnyRole('inspector', 'staff') && (<RegisterDialog row={row} />)}
        {user.hasAnyRole('boatowner', 'staff') && (<BookingDialog row={row} />)}
      </Box>
    ),
  });

  const validateRequired = (value: string) => !!value.length;

  function validateEvent(event: InspectionEventType) {
    return {
      place: !validateRequired(event.place) ? "Location is Required" : "",
      day: !validateRequired(event.day) ? "Date is Required" : "",
    };
  }

  return <MaterialReactTable table={table} />;
}
