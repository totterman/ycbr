import Button from "@mui/material/Button";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useLocation } from "@tanstack/react-router";
import axios from "axios";
import { FormEvent, useState } from "react";

interface LoginOptionDto {
  label: string;
  loginUri: string;
  isSameAuthority: boolean;
}

export default function Login() {
  const [errorMessage, setErrorMessage] = useState("");
  const currentPath = useLocation({
    select: (loc) => loc.pathname,
  });
  const queryClient = useQueryClient();

  const loginOptions = useQuery({
    queryKey: ['loginOptions'],
    queryFn: async () => {
      const response = await axios.get<Array<LoginOptionDto>>("/bff/login-options");
      return response.data;
    },
    select: (data) => data?.[0]?.loginUri || "",
  });

  const loginMutation = useMutation({
    mutationFn: async (loginUri: string) => {
      const url = new URL(loginUri);
      /*
      console.log(
        "post_login_success_uri",
        `${import.meta.env.VITE_REVERSE_PROXY}${currentPath}`
      );
      console.log(
        "post_login_failure_uri",
        `${import.meta.env.VITE_REVERSE_PROXY}/login-error`
      );
      */
      url.searchParams.append(
        "post_login_success_uri",
        `${import.meta.env.VITE_REVERSE_PROXY}${currentPath}`
      );
      url.searchParams.append(
        "post_login_failure_uri",
        `${import.meta.env.VITE_REVERSE_PROXY}/login-error`
      );
      await axios.post(url.toString());
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["user"] });
      window.location.href = loginOptions.data || ""; // redirect after login
    },
    onError: (error: any) => {
      setErrorMessage(
        error.response?.data?.message || "An error occurred during login."
      );
    },
  });

  function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!loginOptions.data) return;
    loginMutation.mutate(loginOptions.data);
  }

  return (
    <span>
      <form onSubmit={onSubmit}>
        <Button
          color="inherit"
          variant="text"
          size="small"
          disabled={loginMutation.isPending}
          type="submit"
        >
          {loginMutation.isPending ? "Logging in..." : "Login"}
        </Button>
      </form>
      {errorMessage && <p className="error">{errorMessage}</p>}
    </span>
  );
}
