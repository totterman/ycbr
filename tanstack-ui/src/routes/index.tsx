import { createFileRoute } from '@tanstack/react-router'
import { Stack, Typography } from '@mui/material'
import { User, useUser } from '@/auth/useUser';
import { useIntlayer, useLocale } from "react-intlayer";

export const Route = createFileRoute('/')({
  component: IndexComponent,
})

  
function IndexComponent() {
  const { user } = useUser();
  const content = useIntlayer("routes");

  const message = user.isAuthenticated
    ? ''.concat(content.hi.value).concat(` ${user.name}, `).concat(content.granted.value).concat(` ${rolesStr(user)}.`)
    : content.notAuthenticated;

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