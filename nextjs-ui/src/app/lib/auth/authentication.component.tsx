"use client";

import Login from "./login";
import Logout from "./logout";
import { useUser } from "./user.service";

export default function Authentication() {
  const { user, isLoading } = useUser();

  if (isLoading) {
    return <span>Loading...</span>;
  }

  return (
    <span>
      {!user.isAuthenticated ? <Login /> : <Logout />}
    </span>
  );}
