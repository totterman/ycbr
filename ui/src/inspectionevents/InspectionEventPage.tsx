import { useUser } from "@/auth/useUser";
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_EditActionButtons,
  MRT_Localization,
  MRT_TableOptions,
  useMaterialReactTable,
} from "material-react-table";
import { useMemo, useState } from "react";
import {
  i9eventsQueryOptions,
  I9EventDto,
  useCreateI9Event,
  validateEvent,
  NewI9EventDto,
} from "./inspectionevent";
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
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";
import { MRT_Localization_EN } from "material-react-table/locales/en";
import { MRT_Localization_FI } from "material-react-table/locales/fi";
import { MRT_Localization_SV } from "material-react-table/locales/sv";

export function InspectionEventPage() {
  const [validationErrors, setValidationErrors] = useState<
    Record<string, string | undefined>
  >({});
  const content = useIntlayer("i9events");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;


  const columns = useMemo<MRT_ColumnDef<I9EventDto>[]>(
    () => [
      {
        accessorFn: (originalRow) => new Date(originalRow.day),
        id: "day",
        header: content.date,
        Edit: ({ cell, column, row, table }) => {
          return (
            <DayDatePicker row={row} validationErrors={validationErrors} />
          );
        },
        size: 150,
        Cell: ({ cell }) => cell.getValue<Date>().toLocaleDateString(tlds),
      },
      {
        accessorKey: "place",
        header: content.location,
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
        header: content.start_time,
        Edit: ({ cell, column, row, table }) => {
          return (
            <FromTimePicker row={row} validationErrors={validationErrors} />
          );
        },
        size: 100,
        Cell: ({ cell }) =>
          cell.getValue<Date>().toLocaleTimeString(locale.substring(0, 2), {
            hour: "2-digit",
            minute: "2-digit",
          }),
      },
      {
        accessorFn: (originalRow) => new Date(originalRow.ends),
        id: "ends",
        header: content.end_time,
        Edit: ({ cell, column, row, table }) => {
          return <ToTimePicker row={row} validationErrors={validationErrors} />;
        },
        size: 100,
        Cell: ({ cell }) =>
          cell.getValue<Date>().toLocaleTimeString(locale.substring(0, 2), {
            hour: "2-digit",
            minute: "2-digit",
          }),
      },
      {
        accessorKey: "inspectors",
        header: content.inspectors,
        enableEditing: false,
        enableColumnFilter: false,
        enableSorting: false,
        size: 100,
      },
      {
        accessorKey: "boats",
        header: content.boats,
        enableEditing: false,
        enableColumnFilter: false,
        enableSorting: false,
        size: 100,
      },
    ],
    [validationErrors, locale]
  );

  // const data = events;
  const eventsQuery = useSuspenseQuery(i9eventsQueryOptions);
  const events = eventsQuery.data;
  const { user, isError } = useUser();

  const { mutateAsync: createI9Event, isPending: isCreatingI9Event } =
    useCreateI9Event();

  const handleCreateI9Event: MRT_TableOptions<I9EventDto>["onCreatingRowSave"] =
    async ({ values, table }) => {
      const newValidationErrors = validateEvent(values, content);
      if (Object.values(newValidationErrors).some((error) => error)) {
        setValidationErrors(newValidationErrors);
        return;
      }
      setValidationErrors({});
      const newEvent: NewI9EventDto = {
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
      };
      await createI9Event(newEvent);
      table.setCreatingRow(null); //exit creating mode
    };

  const mrtLocalization: { [key: string]: MRT_Localization } = {
    "en": MRT_Localization_EN,
    "fi-FI": MRT_Localization_FI,
    "sv-FI": MRT_Localization_SV,
  };

  const table = useMaterialReactTable({
    columns,
    data: events,
    getRowId: (row) => {
      return row.i9eventId === null ? "null" : row.i9eventId;
    },
    createDisplayMode: "modal",
    displayColumnDefOptions: {
      "mrt-row-actions": {
        size: 100,
        muiTableBodyCellProps: { align: "center" },
      },
    },
    enableRowActions: true,
    enableEditing: true,

    initialState: {
      sorting: [
        {
          id: "starts",
          desc: false,
        },
      ],
    },
    localization: mrtLocalization[locale],
    onCreatingRowCancel: () => setValidationErrors({}),
    onCreatingRowSave: handleCreateI9Event,

    renderCreateRowDialogContent: ({ table, row, internalEditComponents }) => (
      <>
        <DialogTitle>{content.create_title}</DialogTitle>
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
          {content.create_title}
        </Button>
      ),
  });

  return <MaterialReactTable table={table} />;
}
