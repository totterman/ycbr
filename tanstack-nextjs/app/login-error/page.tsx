"use client";

import Link from "next/link";
import { useEffect, useState } from "react";

export default function LoginError() {
  const [uri, setUri] = useState("");

  useEffect(() => {
      setUri(window.location.search);
  }, []);
  if (!uri) return <div>Loading...</div>;

  const params = uri ? new URLSearchParams(uri.substring(1)) : new URLSearchParams();

//    const uri = window.location.search;
//    const params = uri ? new URLSearchParams(uri.substring(1)) : new URLSearchParams();

  return (
    <main className="min-h-screen">
      <div className="flex">
        <span className="ml-2"></span>
        <button>
          <Link href="/">Home</Link>
        </button>
        <span className="m-auto"></span>
        <h1>Login error</h1>
        <span className="m-auto"></span>
      </div>
      <div className="flex flex-col items-center justify-between p-24">
        <p>{params.get('error')}</p>
      </div>
    </main>
  );
}
