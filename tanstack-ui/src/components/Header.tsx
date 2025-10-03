import MenuIcon from "@mui/icons-material/Menu";
import Authentication from "@/auth/authentication";
import { CustomLink } from "@/components/CustomLink";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import { css, styled } from "@mui/material";
import { useUser } from "@/auth/useUser";

const StyledCustomLink = styled(CustomLink)(
  ({ theme }) => css`
    color: ${theme.palette.common.white};
  `
);

export function Header() {
  const { user } = useUser();
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
          <StyledCustomLink to="/">Index</StyledCustomLink>
          <StyledCustomLink to="/about">About</StyledCustomLink>
          {user.hasAnyRole("boatowner", "staff", "inspector") && (
            <StyledCustomLink to="/boats">Boats</StyledCustomLink>
          )}
          {user.hasAnyRole("staff", "inspector") && (
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
