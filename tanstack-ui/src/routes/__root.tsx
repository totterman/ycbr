import {
  Link,
  Outlet,
  createRootRouteWithContext,
} from "@tanstack/react-router";
import type { QueryClient } from "@tanstack/react-query";
import YcbrAppBar from "@/components/YcbrAppBar";
import Container from "@mui/material/Container";

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
    <>
      <YcbrAppBar />
      <Container component="main" sx={{ paddingBlock: 4 }}>
        <Outlet />
      </Container>
    </>
  );
}
