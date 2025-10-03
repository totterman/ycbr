import MenuIcon from "@mui/icons-material/Menu";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Toolbar from "@mui/material/Toolbar";
import { CustomLink } from "./CustomLink";
import { css, styled } from "@mui/material";
import { User, useUser } from "@/auth/useUser";
import Authentication from "@/auth/authentication";

const StyledCustomLink = styled(CustomLink)(
  ({ theme }) => css`
    color: ${theme.palette.common.white};
  `
);

export default function YcbrAppBar() {
  const { user, isLoading } = useUser();
  if (isLoading) return <div>Loading...</div>;

  return (
    <AppBar position="static">
      <Toolbar>
        <Box
          sx={{
            flexGrow: 1,
            display: "flex",
            alignItems: "center",
            px: 0,
            gap: 2,
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
            Home
          </StyledCustomLink>{" "}
          <StyledCustomLink
            to="/about"
            activeProps={{
              className: "font-bold",
            }}
          >
            About
          </StyledCustomLink>{" "}
          {user.isAuthenticated && (
            <StyledCustomLink
              to="/boats"
              activeProps={{
                className: "font-bold",
              }}
            >
              Boats
            </StyledCustomLink>
          )}
          {user.hasAnyRole("boatowner", "staff", "inspector") && (
            <StyledCustomLink to="/inspect">Inspections</StyledCustomLink>
          )}
          {user.hasAnyRole("staff") && (
            <StyledCustomLink to="/dispatch">Dispatch</StyledCustomLink>
          )}
        </Box>

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
