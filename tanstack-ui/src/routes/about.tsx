import { createFileRoute } from '@tanstack/react-router'
import { Typography } from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import axios from 'axios';

interface YcbrDto {
  greeting: string;
}

export const Route = createFileRoute('/about')({
  component: RouteComponent,
})

var warning: string = "";

function RouteComponent() {
  const { data, error, isPending } = useQuery({
    queryKey: ['status'],
    queryFn: async () => {
      const { data } = await axios.get<YcbrDto>('/bff/api/ycbr');
      return data;
    }
  });
  if (isPending) warning = "Loading...";
  if (axios.isAxiosError(error)) {
    console.log("Error: ", error.status, error.name, error.message);
    warning = "";
  };

  return <Typography variant="body1">
     This application is a show-case for a React + Vite + Tanstack app consuming a REST
          API through an OAuth2 BFF.
      <div>
        { data?.greeting || warning }
      </div>
  </Typography>
}