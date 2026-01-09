"use client";

import { useIntlayer } from "react-intlayer";
import Login from "./login";
import Logout from "./logout";
import { useUser } from "./useUser";

export default function Authentication() {
  const { user, isLoading } = useUser();
  const content = useIntlayer("auth");
  
  if (isLoading) {
    return <span>{content.loading}...</span>;
  }

  return (
    <span>
      {!user.isAuthenticated ? <Login /> : <Logout />}
    </span>
  );}
