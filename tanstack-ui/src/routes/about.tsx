import { createFileRoute, Link } from "@tanstack/react-router";
import { Typography } from "@mui/material";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { useUser } from "@/auth/useUser";
import { useIntlayer } from "react-intlayer";

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
  const content = useIntlayer("routes");

  if (isLoading) return <div>Loading...</div>;

  return (
    <>
      <Typography variant="body1">
        {content.about}
        {user.isAuthenticated && <GreetingComponent />}
      </Typography>

      <Typography variant="body2">
        {content.author}
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
