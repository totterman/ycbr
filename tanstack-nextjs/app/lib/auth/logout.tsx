"use client";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";

export default function Logout() {
const queryClient = useQueryClient();

  const logoutMutation = useMutation({
    mutationFn: async () => {
      const response = await axios.post(
        "/bff/logout",
        {},
        {
          headers: {
            "X-POST-LOGOUT-SUCCESS-URI": process.env.NEXT_PUBLIC_BASE_URI,
          },
        }
      );
      return response.headers["location"];
    },
    onSuccess: (location: string) => {
      queryClient.invalidateQueries({ queryKey: ["user"] });
      window.location.href = location;
    },
    // Optionally handle errors here
  });

  return (
    <button type="submit" onClick={() => logoutMutation.mutate()} disabled={logoutMutation.isPending}>
      {logoutMutation.isPending ? "Logging out..." : "Logout"}
    </button>
  );
}
