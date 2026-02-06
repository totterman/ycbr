import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_EditActionButtons,
  MRT_Localization,
  MRT_Row,
  MRT_TableOptions,
  useMaterialReactTable,
} from "material-react-table";
import { useMemo, useState } from "react";
import {
  boatQueryOptions,
  boatsQueryOptions,
  BoatType,
  myBoatsQueryOptions,
  useCreateBoat,
  useDeleteBoat,
  useUpdateBoat,
  validateBoat,
} from "./boat";
import { useSuspenseQuery } from "@tanstack/react-query";
import {
  Box,
  Button,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Stack,
  Tooltip,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";
import MinusIcon from "@mui/icons-material/Remove";
import { useUser } from "@/auth/useUser";
import { useIntlayer, useLocale } from "react-intlayer";

import { MRT_Localization_EN } from "material-react-table/locales/en";
import { MRT_Localization_FI } from "material-react-table/locales/fi";
import { MRT_Localization_SV } from "material-react-table/locales/sv";

export default function BoatsPage() {
  const content = useIntlayer("boats");
  const { locale } = useLocale();

  const columns = useMemo<MRT_ColumnDef<BoatType>[]>(
    () => [
      {
        accessorKey: "boatId",
        header: "Id",
        enableEditing: false,
        size: 20,
      },
      {
        accessorKey: "name",
        header: content.boatName,
      },
      {
        accessorKey: "sign",
        header: content.boatSign,
      },
      {
        accessorKey: "kind",
        header: content.boatKind,
      },
      {
        accessorKey: "owner",
        header: content.owner,
      },
      {
        accessorKey: "make",
        header: content.make,
      },
      {
        accessorKey: "model",
        header: content.model,
      },
      {
        accessorKey: "year",
        header: content.year,
      },
      {
        accessorKey: "engines",
        header: content.engine,
      },
      {
        accessorKey: "loa",
        header: content.loa,
      },
      {
        accessorKey: "beam",
        header: content.beam,
      },
      {
        accessorKey: "draft",
        header: content.draft,
      },
      {
        accessorKey: "deplacement",
        header: content.deplacement,
      },
    ],
    [locale]
  );

  const { user, myBoats } = useUser();
  const boats = (user.isStaff ? useSuspenseQuery(boatsQueryOptions).data : myBoats) || [] as BoatType[];

  const [validationErrors, setValidationErrors] = useState<
    Record<string, string | undefined>
  >({});

  const { mutateAsync: createBoat, isPending: isCreatingBoat } =
    useCreateBoat();
  const { mutateAsync: updateBoat, isPending: isUpdatingBoat } =
    useUpdateBoat();
  const { mutateAsync: deleteBoat, isPending: isDeletingBoat } =
    useDeleteBoat();

  const handleCreateBoat: MRT_TableOptions<BoatType>["onCreatingRowSave"] =
    async ({ values, table }) => {
      const newValidationErrors = validateBoat(values, content);
      if (Object.values(newValidationErrors).some((error) => error)) {
        setValidationErrors(newValidationErrors);
        return;
      }
      setValidationErrors({});
      await createBoat(values);
      table.setCreatingRow(null); //exit creating mode
    };

  const handleUpdateBoat: MRT_TableOptions<BoatType>["onEditingRowSave"] =
    async ({ values, table }) => {
      const newValidationErrors = validateBoat(values, content);
      if (Object.values(newValidationErrors).some((error) => error)) {
        setValidationErrors(newValidationErrors);
        return;
      }
      console.log('onEditingRowSave', values),
      setValidationErrors({});
      await updateBoat(values);
      table.setEditingRow(null); //exit editing mode
    };

  const openDeleteConfirmModal = (row: MRT_Row<BoatType>) => {
    if (window.confirm(content.confirmDelete.value)) {
      deleteBoat(row.original.boatId);
    }
  };

  const DetailPanel = ({ row }: { row: MRT_Row<BoatType> }) => {
    const boatQuery = useSuspenseQuery(boatQueryOptions(row.id));
    const boatDetails = boatQuery.data;
    //    if (isLoading) return <CircularProgress />;
    //    if (isError) return <Alert severity="error">Error Loading User Info</Alert>;
    const { engines, year } = boatDetails ?? {};
    return (
      <Stack gap="0.5rem" minHeight="00px">
        <div>
          <b>{content.engine}: </b> {engines}
        </div>
        <div>
          <b>{content.year}: </b> {year}
        </div>
      </Stack>
    );
  };

  const mrtLocalization: { [key: string]: MRT_Localization } = {
    "en": MRT_Localization_EN,
    "fi-FI": MRT_Localization_FI,
    "sv-FI": MRT_Localization_SV,
  };

  const table = useMaterialReactTable({
    columns,
    data: boats,
    createDisplayMode: "modal",
    editDisplayMode: "modal",
    enableEditing: true,
    getRowId: (row) => {
      return row.boatId;
    },
    initialState: {
      columnVisibility: { boatId: false },
      columnOrder: [
        "mrt-row-expand",
        "mrt-row-actions",
        "boatId",
        "name",
        "kind",
        "sign",
        "owner",
        "make",
        "model",
        "year",
        "engines",
        "loa",
        "beam",
        "draft",
        "deplacement",
      ],
    },
    localization: mrtLocalization[locale],
    muiExpandButtonProps: ({ row }) => ({
      children: row.getIsExpanded() ? <MinusIcon /> : <AddIcon />,
    }),
    onCreatingRowCancel: () => setValidationErrors({}),
    onCreatingRowSave: handleCreateBoat,
    onEditingRowCancel: () => setValidationErrors({}),
    onEditingRowSave: handleUpdateBoat,
    renderDetailPanel: ({ row }) => (
      <>
      {(user.hasAnyRole("staff", "boatowner", "inspector")) && (
      <DetailPanel row={row} />)}
      </>
    ),
    renderCreateRowDialogContent: ({ table, row, internalEditComponents }) => (
      <>
        <DialogTitle>{content.createBoat}</DialogTitle>
        <DialogContent
          sx={{ display: "flex", flexDirection: "column", gap: "1rem" }}
        >
          {internalEditComponents} {/* or render custom edit components here */}
        </DialogContent>
        <DialogActions>
          <MRT_EditActionButtons variant="text" table={table} row={row} />
        </DialogActions>
      </>
    ),

    renderEditRowDialogContent: ({ table, row, internalEditComponents }) => (
      <>
        <DialogTitle>{content.updateBoat}</DialogTitle>
        <DialogContent
          sx={{ display: "flex", flexDirection: "column", gap: "1.5rem" }}
        >
          {internalEditComponents} {/* or render custom edit components here */}
        </DialogContent>
        <DialogActions>
          <MRT_EditActionButtons variant="text" table={table} row={row} />
        </DialogActions>
      </>
    ),

    renderRowActions: ({ row, table }) => (
      <Box sx={{ display: "flex", gap: "1rem" }}>
        {(user.hasAnyRole("staff") ||
          (user.hasAnyRole("boatowner") &&
            row.original.owner === user.name)) && (
          <>
            <Tooltip title={content.edit.value}>
              <IconButton onClick={() => table.setEditingRow(row)}>
                <EditIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={content.delete.value}>
              <IconButton
                color="error"
                onClick={() => openDeleteConfirmModal(row)}
              >
                <DeleteIcon />
              </IconButton>
            </Tooltip>
          </>
        )}
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
          {content.createBoat}
        </Button>
      ),
  });

  return <MaterialReactTable table={table} />;
}
