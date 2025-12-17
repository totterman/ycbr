import MenuIcon from "@mui/icons-material/Menu";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Toolbar from "@mui/material/Toolbar";
import { CustomLink } from "./CustomLink";
import { css, styled } from "@mui/material";
import { useUser } from "@/auth/useUser";
import Authentication from "@/auth/authentication";
import ProfileButton from "./ProfileButton";
import LocaleButton from "./LocaleButton";
import { useIntlayer, useLocale } from "react-intlayer";

const StyledCustomLink = styled(CustomLink)(
  ({ theme }) => css`
    color: ${theme.palette.common.white};
  `
);

export default function YcbrAppBar() {
  const { user, isLoading } = useUser();
  const content = useIntlayer("components");

  if (isLoading) return <div>{content.loading}...</div>;

  return (
    <AppBar position="static">
      <Toolbar>
        <Box
          sx={{
            flexGrow: 1,
            display: "flex",
            alignItems: "center",
            px: 0,
            gap: 4,
          }}
        >
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <StyledCustomLink
            to="/"
            activeProps={{
              className: "font-bold",
            }}
            activeOptions={{ exact: true }}
          >
            {content.home}
          </StyledCustomLink>{/*{" "}
          <StyledCustomLink
            to="/about"
            activeProps={{
              className: "font-bold",
            }}
          >
            {content.about}
          </StyledCustomLink>{" "}*/}
          {user.isAuthenticated && (
            <StyledCustomLink
              to="/boats"
              activeProps={{
                className: "font-bold",
              }}
            >
              {content.boats}
            </StyledCustomLink>
          )}
          {user.hasAnyRole("boatowner", "staff", "inspector") && (
            <StyledCustomLink to="/i9event">{content.i9events}</StyledCustomLink>
          )}
          {user.hasAnyRole("staff", "inspector") && (
            <StyledCustomLink to="/dispatch">{content.dispatch}</StyledCustomLink>
          )}
          {user.hasAnyRole("staff", "inspector") && (
            <StyledCustomLink to="/inspect/">{content.inspections}</StyledCustomLink>
          )}
        </Box>
        <LocaleButton />
        <ProfileButton />
        <Box
          sx={{
            display: { xs: "none", md: "flex" },
            gap: 1,
            alignItems: "center",
          }}
        >
          <Authentication />
        </Box>
      </Toolbar>
    </AppBar>
  );
}
