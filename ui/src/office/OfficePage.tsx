import { useUser } from "@/auth/useUser";
import {
  allInspectionsQueryOptions,
  InspectionDto,
  MyInspectionsDto,
} from "@/inspections/inspection";
import { useSuspenseQuery } from "@tanstack/react-query";
import dayjs from "dayjs";
import { Locale } from "intlayer";
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_Localization,
  useMaterialReactTable,
} from "material-react-table";
import { MRT_Localization_EN } from "material-react-table/locales/en";
import { MRT_Localization_FI } from "material-react-table/locales/fi";
import { MRT_Localization_SV } from "material-react-table/locales/sv";
import { useMemo } from "react";
import { useIntlayer, useLocale } from "react-intlayer";

export default function OfficePage() {
  const inspectionsQuery = useSuspenseQuery(allInspectionsQueryOptions);
  const inspections = inspectionsQuery.data;
  const { user } = useUser();

  const content = useIntlayer("office");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;


  const columns = useMemo<MRT_ColumnDef<MyInspectionsDto>[]>(
    () => [
      {
        accessorKey: "inspectionId",
        header: "ID",
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "eventId",
        header: "Event ID",
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "boatId",
        header: "Boat ID",
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "boatName",
        header: content.boat_name,
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "day",
        header: content.inspection_day,
        size: 50,
        enableEditing: false,
        Cell: ({ cell }) => dayjs(cell.getValue<string>()).toDate().toLocaleDateString(tlds),
      },
      {
        accessorKey: "place",
        header: content.location,
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "inspectorName",
        header: content.inspector,
        size: 50,
        enableEditing: false,
      },
      {
        accessorKey: "completed",
        header: content.inspection_completed,
        size: 50,
        enableEditing: false,
        Cell: ({ cell }) => cell.getValue() == null ? null : dayjs(cell.getValue<string>()).toDate().toLocaleDateString(tlds),
      },
    ],
    [locale]
  );

  const mrtLocalization: { [key: string]: MRT_Localization } = {
    "en": MRT_Localization_EN,
    "fi-FI": MRT_Localization_FI,
    "sv-FI": MRT_Localization_SV,
  };

  const table = useMaterialReactTable({
    columns,
    data: inspections,
    initialState: {
      columnVisibility: { inspectionId: false, eventId: false, boatId: false },
    },
    localization: mrtLocalization[locale],
  });

  return <MaterialReactTable table={table} />;
}
