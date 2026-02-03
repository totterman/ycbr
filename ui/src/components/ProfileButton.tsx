import { useState } from "react";
import { useIntlayer } from "react-intlayer";
import { CustomLink } from "./CustomLink";
import IconButton from "@mui/material/IconButton";
import AccountCircle from "@mui/icons-material/AccountCircle";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Link from "@mui/material/Link";
import { useUser } from "@/auth/useUser";

export default function ProfileButton() {
  const content = useIntlayer("components");
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const { user, myBoats, myInspections } = useUser();

  const handleMyBoats = () => {
    console.log("handleMyBoats");
    handleClose();
  };

  const handleMyInspections = () => {
    console.log("handleMyInspections");
    handleClose();
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <div>
      <IconButton
        size="large"
        aria-label="account of current user"
        aria-controls="menu-appbar"
        aria-haspopup="true"
        onClick={handleMenu}
        color="inherit"
      >
        <AccountCircle />
      </IconButton>
      <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        open={Boolean(anchorEl)}
        onClick={handleClose}
        onClose={handleClose}
      >
        {user.hasAnyRole("staff", "boatowner") && (
        <MenuItem>
          <CustomLink to="/boats" underline="none">
            {user.isStaff ? content.boats : content.my_boats}
          </CustomLink>
        </MenuItem>
        )}
        {user.hasAnyRole("staff", "inspector") && (
        <MenuItem>
          <CustomLink to="/inspect" underline="none">
            {user.isStaff ? content.inspections : content.my_inspections}
          </CustomLink>
        </MenuItem>
        )}
      </Menu>
    </div>
  );
}
