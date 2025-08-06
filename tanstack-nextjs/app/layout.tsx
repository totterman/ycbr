"use client";

import { Inter } from "next/font/google";
import "./globals.css";
import Authentication from "./lib/auth/authentication.component";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
// import { useUser } from "./lib/auth/user.service";

const inter = Inter({ subsets: ["latin"] });
const queryClient = new QueryClient();

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
// const { user, isLoading, isError } = useUser();

  return (
    <html lang="en">
      <body className={inter.className}>
        <QueryClientProvider client={queryClient}>
          <div className="flex">
            <div className="m-auto"></div>
            <h1 className="mt-2">React UI</h1>
            <div className="m-auto"></div>
            <div className="mt-2">
              <Authentication />
            </div>
            <div className="mr-3"></div>
          </div>
          {children}
        </QueryClientProvider>
      </body>
    </html>
  );
}
