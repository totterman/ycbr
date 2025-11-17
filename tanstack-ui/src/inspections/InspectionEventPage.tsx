import { useUser } from "@/auth/useUser";
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_EditActionButtons,
  MRT_TableOptions,
  useMaterialReactTable,
} from "material-react-table";
import { useMemo, useState } from "react";
import {
  i9eventsQueryOptions,
  InspectionDao,
  useCreateI9Event,
  validateEvent,
} from "./inspection";
import { places } from "./static_inspectiondata";
import {
  Box,
  Button,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import RegisterDialog from "./RegisterDialog";
import BookingDialog from "./BookingDialog";
import { useSuspenseQuery } from "@tanstack/react-query";
import {
  DayDatePicker,
  FromTimePicker,
  ToTimePicker,
} from "./EventDateTimePickers";

export function InspectionEventPage() {
  const [validationErrors, setValidationErrors] = useState<
    Record<string, string | undefined>
  >({});

  const columns = useMemo<MRT_ColumnDef<InspectionDao>[]>(
    () => [
      {
        accessorFn: (originalRow) => new Date(originalRow.day),
        id: "day",
        header: "Date",
        Edit: ({ cell, column, row, table }) => {
          return (
            <DayDatePicker row={row} validationErrors={validationErrors} />
          );
        },
        size: 150,
        Cell: ({ cell }) => cell.getValue<Date>().toLocaleDateString("fi-FI"),
      },
      {
        accessorKey: "place",
        header: "Location",
        editVariant: "select",
        editSelectOptions: places,
        muiEditTextFieldProps: {
          select: true,
          error: !!validationErrors?.place,
          helperText: validationErrors?.place,
          variant: "outlined",
          InputLabelProps: { shrink: true },
        },
        size: 150,
      },
      {
        accessorFn: (originalRow) => new Date(originalRow.starts),
        id: "starts",
        header: "Start time",
        Edit: ({ cell, column, row, table }) => {
          return (
            <FromTimePicker row={row} validationErrors={validationErrors} />
          );
        },
        size: 100,
        Cell: ({ cell }) =>
          cell.getValue<Date>().toLocaleTimeString("sv-FI", {
            hour: "2-digit",
            minute: "2-digit",
          }),
      },
      {
        accessorFn: (originalRow) => new Date(originalRow.ends),
        id: "ends",
        header: "End time",
        Edit: ({ cell, column, row, table }) => {
          return <ToTimePicker row={row} validationErrors={validationErrors} />;
        },
        size: 100,
        Cell: ({ cell }) =>
          cell.getValue<Date>().toLocaleTimeString("sv-FI", {
            hour: "2-digit",
            minute: "2-digit",
          }),
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
    [validationErrors]
  );

  // const data = events;
  const eventsQuery = useSuspenseQuery(i9eventsQueryOptions);
  const events = eventsQuery.data;
  const { user, isError } = useUser();

  const { mutateAsync: createI9Event, isPending: isCreatingI9Event } =
    useCreateI9Event();

  const handleCreateI9Event: MRT_TableOptions<InspectionDao>["onCreatingRowSave"] =
    async ({ values, table }) => {
      const newValidationErrors = validateEvent(values);
      if (Object.values(newValidationErrors).some((error) => error)) {
        setValidationErrors(newValidationErrors);
        return;
      }
      setValidationErrors({});
      const newEvent: InspectionDao = {
        id: null,
        place: values.place,
        day: values.day,
        starts:
          (values.day as string).split("T")[0] +
          "T" +
          (values.starts as string).split("T")[1],
        ends:
          (values.day as string).split("T")[0] +
          "T" +
          (values.ends as string).split("T")[1],
        inspectors: 0,
        boats: 0,
      };
      await createI9Event(newEvent);
      table.setCreatingRow(null); //exit creating mode
    };

  const table = useMaterialReactTable({
    columns,
    data: events,
    getRowId: (row) => {
      return row.id === null ? "null" : row.id.toString();
    },
    createDisplayMode: "modal",
    displayColumnDefOptions: {
      "mrt-row-actions": {
        size: 300,
        muiTableBodyCellProps: { align: "center" },
      },
    },
    enableRowActions: true,
    enableEditing: true,
    onCreatingRowCancel: () => setValidationErrors({}),
    onCreatingRowSave: handleCreateI9Event,

    initialState: {
      sorting: [
        {
          id: "starts",
          desc: false,
        },
      ],
    },

    renderCreateRowDialogContent: ({ table, row, internalEditComponents }) => (
      <>
        <DialogTitle>Create New Inspection Event</DialogTitle>
        <DialogContent
          sx={{ display: "flex", flexDirection: "column", gap: "1rem" }}
        >
          {internalEditComponents}
        </DialogContent>
        <DialogActions>
          <MRT_EditActionButtons variant="text" table={table} row={row} />
        </DialogActions>
      </>
    ),

    renderRowActions: ({ row, table }) => (
      <Box sx={{ display: "flex", gap: "1rem" }}>
        {user.hasAnyRole("inspector", "staff") && <RegisterDialog row={row} />}
        {user.hasAnyRole("boatowner", "staff") && <BookingDialog row={row} />}
      </Box>
    ),

    renderTopToolbarCustomActions: ({ table }) =>
      user.hasAnyRole("staff") && (
        <Button
          variant="contained"
          onClick={() => {
            table.setCreatingRow(true);
          }}
        >
          Create New Inspection Event
        </Button>
      ),
  });

  return <MaterialReactTable table={table} />;
}
