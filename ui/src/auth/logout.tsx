import Button from "@mui/material/Button";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { useIntlayer } from "react-intlayer";

export default function Logout() {
  const content = useIntlayer("auth");
  const queryClient = useQueryClient();

  const logoutMutation = useMutation({
    mutationFn: async () => {
      const response = await axios.post(
        "/bff/logout",
        {},
        {
          headers: {
            "X-POST-LOGOUT-SUCCESS-URI": import.meta.env.VITE_BASE_URI,
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
    <Button
      color="inherit"
      variant="text"
      size="small"
      type="submit"
      onClick={() => logoutMutation.mutate()}
      disabled={logoutMutation.isPending}
    >
      {logoutMutation.isPending ? content.logging_out : content.logout}
    </Button>
  );
}
