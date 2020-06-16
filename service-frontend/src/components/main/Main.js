import React, { useMemo, useRef, useState } from "react";
import { NavLink, Switch, Route } from "react-router-dom";
import cls from "./main.module.css";
import clsx from "clsx";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
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
import AccessibilityNewIcon from '@material-ui/icons/AccessibilityNew';
import FolderOpenIcon from '@material-ui/icons/FolderOpen';
import LockIcon from '@material-ui/icons/Lock';
import HowToRegIcon from '@material-ui/icons/HowToReg';
import Users from '../users/list/Users'
import Products from '../products/list/Products'

const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
    },
    appBar: {
      zIndex: theme.zIndex.drawer + 1,
      transition: theme.transitions.create(["width", "margin"], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
    },
    appBarShift: {
      marginLeft: drawerWidth,
      width: `calc(100% - ${drawerWidth}px)`,
      transition: theme.transitions.create(["width", "margin"], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    menuButton: {
      marginRight: 36,
    },
    hide: {
      display: "none",
    },
    drawer: {
      width: drawerWidth,
      flexShrink: 0,
      whiteSpace: "nowrap",
    },
    drawerOpen: {
      width: drawerWidth,
      transition: theme.transitions.create("width", {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    drawerClose: {
      transition: theme.transitions.create("width", {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      overflowX: "hidden",
      width: theme.spacing(7) + 1,
      [theme.breakpoints.up("sm")]: {
        width: theme.spacing(9) + 1,
      },
    },
    toolbar: {
      display: "flex",
      alignItems: "center",
      justifyContent: "flex-start",
      padding: theme.spacing(0, 1),
      // necessary for content to be below app bar
      ...theme.mixins.toolbar,
    },
    content: {
      marginTop: theme.spacing(7),
      flexGrow: 1,
      padding: theme.spacing(3),
    },
    icon: {
      fontSize: 40,
      marginRight: theme.spacing(2),
      [theme.breakpoints.down("xs")]: {
        fontSize: 30,
        marginRight: theme.spacing(3),
      },
    },
  })
);

const Main = (props) => {
  const [state, nextState] = useState({auth: true});
  const classes = useStyles();
  const menuLinks = useMemo(
    () => [
      {
        dest: "/",
        text: "Login",
        icon: <AccessibilityNewIcon className={classes.icon} />,
        exact: true,
        render: (auth) => !auth,
        click: () => {
          nextState(currentState => ({auth:!currentState.auth}));
        },
      },
      {
        dest: "/",
        text: "Logout",
        icon: <LockIcon className={classes.icon} />,
        exact: true,
        click: () => {
          nextState(currentState => ({auth:!currentState.auth}));
        },
        render: (auth) => auth,
      },
      {
        dest: "/products/list",
        text: "Products",
        icon: <FolderOpenIcon className={classes.icon} />,
        exact: false,
      },
      {
        dest: "/users/list",
        text: "Users",
        icon: <HowToRegIcon className={classes.icon} />,
        exact: false,
        render: (auth) => auth,
      },
    ],
    [classes]
  );

  const [open, setOpen] = React.useState(false);

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
            (link.render != null ? link.render(state.auth) : true) ? (
              <NavLink
                to={link.dest}
                activeClassName={[cls.linkActive, cls.navLink].join(" ")}
                className={cls.navLink}
                exact={link.exact}
                key={link.text}
                onClick={link.click}
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
        <Switch>
          <Route
            path="/"
            exact
            render={() => (
              <div>
                <Typography paragraph>Lorem ipsum dolor sit amet.</Typography>
                <Typography paragraph>
                  Consequat mauris nunc congue nisi vitae suscipit.
                </Typography>
              </div>
            )}
          />
          <Route path="/users/list" render={Users}/>
          <Route path="/products/list" render={Products}/>
        </Switch>
      </main>
    </div>
  );
};

export default Main;
