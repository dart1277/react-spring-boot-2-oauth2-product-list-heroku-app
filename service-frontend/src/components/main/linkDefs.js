import React from 'react';
import AccessibilityNewIcon from "@material-ui/icons/AccessibilityNew";
import FolderOpenIcon from "@material-ui/icons/FolderOpen";
import LockIcon from "@material-ui/icons/Lock";
import HowToRegIcon from "@material-ui/icons/HowToReg";
const linkDefs =  (classes) => {

    const linksArr =
        [
            {
              dest: "/login",
              text: "Login",
              icon: <AccessibilityNewIcon className={classes.icon} />,
              exact: true,
              render: (auth) => !auth,
            },
            {
              dest: "/logout",
              text: "Logout",
              icon: <LockIcon className={classes.icon} />,
              exact: true,
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
          ];
          return linksArr;
} 

export default linkDefs;

