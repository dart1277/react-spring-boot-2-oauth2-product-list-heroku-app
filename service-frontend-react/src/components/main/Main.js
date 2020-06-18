import React, { useState, useCallback } from "react";
import { NavLink, withRouter } from "react-router-dom";
import cls from "./main.module.css";
import clsx from "clsx";
import Drawer from "@material-ui/core/Drawer";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import MenuIcon from "@material-ui/icons/Menu";
import CloseIcon from "@material-ui/icons/Close";
import { useSelector } from "react-redux";
import { isAuthenticated } from "../../app/reducers/auth/authSlice";
import { useStyles } from "./styles/mainStyles";
import MainRoutes from "./routing/MainRoutes";
import linkDefs from "./linkDefs";

const Main = (props) => {
  const authenticated = useSelector(isAuthenticated);
  const classes = useStyles();

  const menuLinks = useCallback(linkDefs(classes), [classes]);

  const [open, setOpen] = useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = (event) => {
    setOpen(false);
  };

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, {
              [classes.hide]: open,
            })}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            OAuth2 Products
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        className={clsx(classes.drawer, {
          [classes.drawerOpen]: open,
          [classes.drawerClose]: !open,
        })}
        classes={{
          paper: clsx({
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open,
          }),
        }}
      >
        <div className={classes.toolbar}>
          <IconButton onClick={handleDrawerClose}>
            <CloseIcon />
          </IconButton>
        </div>
        <Divider />
        <List>
          {menuLinks.map((link, index) =>
            (link.render != null ? link.render(authenticated) : true) ? (
              <NavLink
                to={link.dest}
                activeClassName={[cls.linkActive, cls.navLink].join(" ")}
                className={cls.navLink}
                exact={link.exact}
                key={link.text}
              >
                <ListItem button>
                  {link.icon}
                  <ListItemText primary={link.text} />
                </ListItem>
              </NavLink>
            ) : (
              ""
            )
          )}
        </List>
        <Divider />
      </Drawer>
      <main className={classes.content}>
        <MainRoutes authenticated={authenticated} />
      </main>
    </div>
  );
};

export default withRouter(Main);
