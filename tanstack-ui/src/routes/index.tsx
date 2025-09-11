import { createFileRoute } from '@tanstack/react-router'
import { Stack, Typography } from '@mui/material'
import z from 'zod'
import { User, useUser } from '@/lib/auth/useUser';

export const Route = createFileRoute('/')({
  validateSearch: z.object({
    count: z.number().optional(),
  }),
  component: RouteComponent,
})

  
function RouteComponent() {
  const { user, isLoading } = useUser();

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
    <Stack alignItems="center">
      <Typography variant="body1" marginBlockEnd={4}>
        { message }
      </Typography>
    </Stack>
  )
}