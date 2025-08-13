import Login from "@/components/auth/login";
import Logout from "@/components/auth/logout";
import { useUser } from "@/hooks/useUser";

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