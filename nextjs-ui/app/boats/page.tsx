"use client";

import axios, { AxiosError, isAxiosError } from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

type Greeting = {
  greeting: string;
}

const EMPTYGREETING: Greeting = {
  greeting: ""
};

const ERROR403GREETING: Greeting = {
  greeting: "You are not authorized to do that!"
};


export default function Boats() {
  const [greeting, setGreeting] = useState<Greeting>(EMPTYGREETING);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchServerGreeting() {
      try {
        const response = await axios.get('/bff/api/ycbr/boats');
        setGreeting(response.data);
      } catch (error: unknown) {
        console.error('Error fetching posts:', error);
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
    <main className="min-h-screen">
      <div className="flex">
        <span className="ml-2"></span>
        <button>
          <Link href="/">Home</Link>
        </button>
        <span className="m-auto"></span>
        <h1>Boats</h1>
        <span className="m-auto"></span>
      </div>
      <div className="flex flex-col items-center justify-between p-24">
        <p>
            This is the boats page. You can add your boat here.
        </p>
        <p>
          { greeting.greeting }
        </p>
      </div>
    </main>
  );
}
