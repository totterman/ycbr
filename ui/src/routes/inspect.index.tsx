import { useUser } from "@/auth/useUser";
import InspectionsPage from "@/inspections/InspectionsPage";
import { createFileRoute, Link, redirect } from "@tanstack/react-router";

export const Route = createFileRoute("/inspect/")({
  component: InspectionsPage,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on inspect route</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
  errorComponent: () => {
    return (
      <div>
        <p>This is the ErrorComponent configured on inspect route</p>
        <Link to="/inspect">Try again</Link>
      </div>
    );
  },
  beforeLoad: () => {
    const { user } = useUser();
    if(!user.isAuthenticated) {
      throw redirect({
        to: '/',
        search: {
          redirect: location.href,
        },
      })
    }
  }
});
