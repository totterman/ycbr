"use client";

import { User, useUser } from "@/src/app/lib/auth/user.service";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";

export default function Home() {
  const { user, isLoading } = useUser();

  if (isLoading) {
    return <span>Loading...</span>;
  }

  const message = user.isAuthenticated
    ? `Hi ${user.name}, you are granted with ${rolesStr(user)}.`
    : "You are not authenticated.";

  function rolesStr(user: User) {
    if (!user?.roles?.length) {
      return "[]";
    }
    return `["${user.roles.join('", "')}"]`;
  }

  return (
    <>
    <div>
      a div
    </div>
    <Container maxWidth="lg">
      <Box
        sx={{
          my: 4,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Typography variant="h4" component="h1" sx={{ mb: 2 }}>
        {message}
        </Typography>
      </Box>
      </Container>
      </ >
  );
}
