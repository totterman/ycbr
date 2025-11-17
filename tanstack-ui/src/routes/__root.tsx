import {
  Link,
  Outlet,
  createRootRouteWithContext,
} from "@tanstack/react-router";
import type { QueryClient } from "@tanstack/react-query";
import YcbrAppBar from "@/components/YcbrAppBar";
import Container from "@mui/material/Container";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import 'dayjs/locale/sv';
import 'dayjs/locale/fi';
import 'dayjs/locale/en';
import dayjs, { Dayjs } from "dayjs";
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';

dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.tz.setDefault(Intl.DateTimeFormat().resolvedOptions().timeZone); // read from settings

export const Route = createRootRouteWithContext<{
  queryClient: QueryClient;
}>()({
  component: RootComponent,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on root route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
});

function RootComponent() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="sv">
      <YcbrAppBar />
      <Container component="main" sx={{ paddingBlock: 4 }}>
        <Outlet />
      </Container>
    </LocalizationProvider>
  );
}
