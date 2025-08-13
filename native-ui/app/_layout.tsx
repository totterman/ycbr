import { useUser } from "@/hooks/useUser";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Stack } from "expo-router";

const queryClient = new QueryClient();

{/* 
export default function RootLayout() {
  return (
    <QueryClientProvider client={queryClient}>
      <Stack>
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
      </Stack>
    </QueryClientProvider>
  );
}
*/}

export default function Root() {

  // Set up the auth context and render our layout inside of it.
  return (
    <QueryClientProvider client={queryClient}>
      {/* <SplashScreenController /> */}
      <RootNavigator />
    </QueryClientProvider>
  );
}

// Separate this into a new component so it can access the SessionProvider context later
function RootNavigator() {
  const { user } = useUser();
  console.log("User is authenticated: ", user.isAuthenticated);

  return (
    <Stack>
      <Stack.Protected guard={user.isAuthenticated}>
        <Stack.Screen name="(tabs)" />
      </Stack.Protected>

      <Stack.Protected guard={!user.isAuthenticated}>
        <Stack.Screen name="sign-in" />
      </Stack.Protected>
    </Stack>
  );
}