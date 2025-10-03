import { createFileRoute, Link } from "@tanstack/react-router";
import { Typography } from "@mui/material";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { useUser } from "@/auth/useUser";

interface YcbrDto {
  greeting: string;
}

export const Route = createFileRoute("/about")({
  component: AboutComponent,
  notFoundComponent: () => {
    return (
      <div>
        <p>This is the notFoundComponent configured on About page</p>
        <Link to="/">Start Over</Link>
      </div>
    );
  },
});

function AboutComponent() {
  const { user, isLoading } = useUser();

  if (isLoading) return <div>Loading...</div>;

  return (
    <>
      <Typography variant="body1">
        This application is a show-case for a React + Vite + Tanstack app
        consuming a REST API through an OAuth2 BFF.
        {user.isAuthenticated && <GreetingComponent />}
      </Typography>

      <Typography variant="body2">
        Written by Petri Tötterman, AB Smartbass, 2025
      </Typography>
    </>
  );
}

function GreetingComponent() {
  var warning: string = "";
  const { data, error, isPending } = useQuery({
    queryKey: ["status"],
    queryFn: async () => {
      const { data } = await axios.get<YcbrDto>("/bff/api/ycbr");
      return data;
    },
  });
  if (isPending) warning = "Loading...";
  if (axios.isAxiosError(error)) {
    console.log("Error: ", error.status, error.name, error.message);
    warning = "";
  }
  return <p>{data && data.greeting}</p>;
}
