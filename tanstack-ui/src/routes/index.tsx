import { createFileRoute } from '@tanstack/react-router'
import { Stack, Typography } from '@mui/material'
import { User, useUser } from '@/auth/useUser';

export const Route = createFileRoute('/')({
  component: IndexComponent,
})

  
function IndexComponent() {
  const { user } = useUser();

  const message = user.isAuthenticated
    ? `Hi ${user.name}, you are granted with roles ${rolesStr(user)}.`
    : "You are not authenticated.";

  function rolesStr(user: User) {
    if (!user?.roles?.length) {
      return "";
    }
    return `"${user.roles.join('", "')}"`;
  }
  return (
    <Stack alignItems="center">
      <Typography variant="body1" marginBlockEnd={4}>
        { message }
      </Typography>
    </Stack>
  )
}