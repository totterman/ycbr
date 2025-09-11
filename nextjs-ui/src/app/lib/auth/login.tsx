"use client";

import Button from "@mui/material/Button";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { usePathname } from "next/navigation";
import { FormEvent, useEffect, useState } from "react";


interface LoginOptionDto {
  label: string;
  loginUri: string;
  isSameAuthority: boolean;
}
async function getLoginOptions(): Promise<Array<LoginOptionDto>> {
  const response = await axios.get<Array<LoginOptionDto>>("/bff/login-options");
  return response.data;
}

export default function Login() {
  const [loginUri, setLoginUri] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const currentPath = usePathname();
    const queryClient = useQueryClient();

    useEffect(() => {
      fetchLoginOptions();
      // eslint-disable-next-line
    }, []);

    async function fetchLoginOptions() {
      const loginOpts = await getLoginOptions();
      setLoginUri(loginOpts?.[0]?.loginUri || "");
    }

const loginMutation = useMutation({
    mutationFn: async (loginUri: string) => {
      const url = new URL(loginUri);
      url.searchParams.append(
        "post_login_success_uri",
        `${process.env.NEXT_PUBLIC_BASE_URI}${currentPath}`
      );
      url.searchParams.append(
        "post_login_failure_uri",
        `${process.env.NEXT_PUBLIC_BASE_URI}/login-error`
      );
  console.log("post_login_success_uri: ", `${process.env.NEXT_PUBLIC_BASE_URI}${currentPath}`);
  console.log("post_login_failure_uri: ", `${process.env.NEXT_PUBLIC_BASE_URI}/login-error`);
      await axios.post(url.toString());
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["user"] });
      window.location.href = loginUri; // redirect after login
    },
    onError: (error: any) => {
      setErrorMessage(
        error.response?.data?.message || "An error occurred during login."
      );
    },
  });

  function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!loginUri) return;
    loginMutation.mutate(loginUri);
  }


  return (
    <span>
      <form onSubmit={onSubmit}>
        <Button color="primary" variant="text" size="small" disabled={loginMutation.isPending} type="submit">
          {loginMutation.isPending ? "Logging in..." : "Login"}
        </Button>
      </form>
      {errorMessage && <p className="error">{errorMessage}</p>}
    </span>
  );
}
