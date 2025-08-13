"use client";

import Link from "next/link";
import { User, useUser } from "./lib/auth/user.service";
import MarketingPage from "@/templates/marketing-page/MarketingPage";

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
    <main className="min-h-screen">
      <div className="flex">
        <span className="ml-2"></span>
        <button>
          <Link href="/about">About</Link>
        </button>
        <span className="m-auto"></span>
        <h1>Home</h1>
        <span className="m-auto"></span>
      </div>
      <div className="flex flex-col items-center justify-between p-24">
        <p>{message}</p>
        <p>{user.isAuthenticated ? <Link href="/boats">Boats</Link> : ""}</p>
      </div>

    </main>
  );
}
