"use client";

import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import axios, { AxiosError, isAxiosError } from "axios";
import { useEffect, useState } from "react";

type Greeting = {
  greeting: string;
};

const EMPTYGREETING: Greeting = {
  greeting: "",
};

const ERROR403GREETING: Greeting = {
  greeting: "You need to be authorized to do that.",
};

export default function Boats() {
  const [greeting, setGreeting] = useState<Greeting>(EMPTYGREETING);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchServerGreeting() {
      try {
        const response = await axios.get("/bff/api/ycbr/boats");
        setGreeting(response.data);
      } catch (error: unknown) {
        console.error("Error fetching posts:", error);
        if (isAxiosError<Greeting>(error)) {
          const axerr = error as AxiosError;
          if (axerr.status === 403) {
            setGreeting(ERROR403GREETING);
          }
        }
      } finally {
        setLoading(false);
      }
    }
    fetchServerGreeting();
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <Container maxWidth="lg">
      <Box
        sx={{
          my: 4,
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Typography variant="h4" component="h1" sx={{ mb: 2 }}>
          {" "}
          This is the boats page. You can add your boat here if you own one.
          {greeting.greeting}
        </Typography>
      </Box>
    </Container>
  );
}
