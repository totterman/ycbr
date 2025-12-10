import IconButton from "@mui/material/IconButton";
import LanguageIcon from '@mui/icons-material/Language';
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useState } from "react";
import { useLocale } from "react-intlayer";
import { getLocaleName } from "intlayer";

export default function LocaleButton() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const { locale, setLocale, availableLocales } = useLocale();

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const open = Boolean(anchorEl);

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <IconButton
        size="large"
        aria-label="language-selector"
        aria-controls={open ? 'menu-locale' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
        color="inherit"
      >
        <LanguageIcon />
      </IconButton>
      <Menu
        id="menu-locale"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        onClick={handleClose}
      >
        {availableLocales.map((loc) => (
          <MenuItem onClick={(e) => setLocale(loc)}>
            {getLocaleName(loc)}
          </MenuItem>
        ))}
      </Menu>
    </>
  );
}
