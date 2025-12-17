import { createFileRoute } from "@tanstack/react-router";
import {
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import { User, useUser } from "@/auth/useUser";
import { useIntlayer, useLocale } from "react-intlayer";

export const Route = createFileRoute("/")({
  component: IndexComponent,
});

function IndexComponent() {
  const { user } = useUser();
  const content = useIntlayer("routes");

  const message = user.isAuthenticated
    ? ""
        .concat(content.hi.value)
        .concat(` ${user.name}, `)
        .concat(content.granted.value)
        .concat(` ${rolesStr(user)}.`)
    : content.notAuthenticated;

  function rolesStr(user: User) {
    if (!user?.roles?.length) {
      return "";
    }
    return `"${user.roles.join('", "')}"`;
  }

  function createData(name: string, sign: string, pw: string, role: string) {
    return { name, sign, pw, role };
  }
  const rows = [
    createData("Stina Skeppare", "stina", "stina", content.boatowner_title),
    createData("Ronja Rorsman", "ronja", "ronja", content.boatowner_title),
    createData("Kalle Kanslist", "kalle", "kalle", content.staff_title),
    createData("Bengt Besiktningsman", "bengt", "bengt", content.inspector_title),
    createData("Jenny Gast", "jenny", "jenny", content.guest_title),
  ];

  return (
    <>
      <Typography variant="h5">{content.app_title}</Typography>

      <Typography variant="body1">{content.app_text}</Typography>

      <Typography variant="h6">{content.users_title}</Typography>

      <Typography variant="body1">{content.users_text}</Typography>

      <TableContainer>
        <Table
          sx={{ maxWidth: 500, margin: 4 }}
          size="small"
          aria-label="a dense table"
        >
          <TableHead>
            <TableRow>
              <TableCell>{content.user_name}</TableCell>
              <TableCell align="center">{content.user_sign}</TableCell>
              <TableCell align="center">{content.user_password}</TableCell>
              <TableCell align="center">{content.user_role}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow
                key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell align="center">{row.sign}</TableCell>
                <TableCell align="center">{row.pw}</TableCell>
                <TableCell align="center">{row.role}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Typography variant="h6">{content.boats_title}</Typography>
      <Typography variant="body1">{content.boats_text}</Typography>

      <Typography variant="h6">{content.events_title}</Typography>
      <Typography variant="body1">{content.events_text}</Typography>

      <Typography variant="h6">{content.inspections_title}</Typography>
      <Typography variant="body1">{content.inspections_text}</Typography>

      <Typography variant="h6">{content.boatowner_title}</Typography>
      <Typography variant="body1">{content.boatowner_text}</Typography>

      <Typography variant="h6">{content.inspector_title}</Typography>
      <Typography variant="body1">{content.inspector_text}</Typography>

      <Typography variant="h6">{content.staff_title}</Typography>
      <Typography variant="body1">{content.staff_text}</Typography>

      <Typography variant="h6">{content.guest_title}</Typography>
      <Typography variant="body1">{content.guest_text}</Typography>

      <Typography variant="body1" sx={{ mt: 24 }}>
        {content.about}
      </Typography>

      <Typography variant="body2">{content.author}</Typography>
    </>
  );
}
