import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_EditActionButtons,
  MRT_Row,
  MRT_TableOptions,
  useMaterialReactTable,
} from "material-react-table";
import { useMemo, useState } from "react";
import {
  boatQueryOptions,
  boatsQueryOptions,
  BoatType,
  useCreateBoat,
  useDeleteBoat,
  useUpdateBoat,
  validateBoat,
} from "./boat";
import { useSuspenseQuery } from "@tanstack/react-query";
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Stack,
  Tooltip,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import SailingIcon from "@mui/icons-material/SailingSharp";
import AddIcon from "@mui/icons-material/Add";
import MinusIcon from "@mui/icons-material/Remove";
import { useUser } from "@/auth/useUser";
import { useNavigate } from "@tanstack/react-router";

export default function BoatsPage() {
  const columns = useMemo<MRT_ColumnDef<BoatType>[]>(
    () => [
      {
        accessorKey: "id",
        header: "Id",
        enableEditing: false,
        //        size: 80,
      },
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
        accessorKey: "year",
        header: "Year",
      },
      {
        accessorKey: "engines",
        header: "Engine",
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
  const { user } = useUser();

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
      const newValidationErrors = validateBoat(values);
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
      const newValidationErrors = validateBoat(values);
      if (Object.values(newValidationErrors).some((error) => error)) {
        setValidationErrors(newValidationErrors);
        return;
      }
      setValidationErrors({});
      await updateBoat(values);
      table.setEditingRow(null); //exit editing mode
    };

  const openDeleteConfirmModal = (row: MRT_Row<BoatType>) => {
    if (window.confirm("Are you sure you want to delete this boat?")) {
      deleteBoat(row.original.id);
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
          <b>Engine: </b> {engines}
        </div>
        <div>
          <b>Year: </b> {year}
        </div>
      </Stack>
    );
  };

  const table = useMaterialReactTable({
    columns,
    data: boats,
    createDisplayMode: "modal",
    editDisplayMode: "modal",
    enableEditing: true,
    getRowId: (row) => {
      return row.id.toString();
    },
    initialState: {
      columnVisibility: { id: false },
      columnOrder: [
        "mrt-row-expand",
        "mrt-row-actions",
        "id",
        "name",
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
    muiExpandButtonProps: ({ row }) => ({
      children: row.getIsExpanded() ? <MinusIcon /> : <AddIcon />,
    }),
    onCreatingRowCancel: () => setValidationErrors({}),
    onCreatingRowSave: handleCreateBoat,
    onEditingRowCancel: () => setValidationErrors({}),
    onEditingRowSave: handleUpdateBoat,
    renderDetailPanel: ({ row }) => <DetailPanel row={row} />,
    renderCreateRowDialogContent: ({ table, row, internalEditComponents }) => (
      <>
        <DialogTitle>Create New Boat</DialogTitle>
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
        <DialogTitle>Update Boat</DialogTitle>
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
            <Tooltip title="Edit">
              <IconButton onClick={() => table.setEditingRow(row)}>
                <EditIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Delete">
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
          Create New Boat
        </Button>
      ),
  });

  return <MaterialReactTable table={table} />;
}
