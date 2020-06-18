import React from "react";
import IconButton from "@material-ui/core/IconButton";
import LoginImg from "./img/btn_google_dark_normal_ios.svg";
import Icon from "@material-ui/core/Icon";
import { withRouter } from "react-router-dom";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
const SERVER_DOMAIN_PORT = "localhost:8080";
const CLIENT_DOMAIN_PORT = "localhost:3000";
const PROTOCOL = "http";
const GOOGLE_PROVIDER = "google";

const getAuthUrl = (provider) => 
  PROTOCOL +
  "://" +
  SERVER_DOMAIN_PORT +
  "/oauth2/authorize/" +
  provider +
  "?redirect_uri=" +
  PROTOCOL +
  "://" +
  CLIENT_DOMAIN_PORT +
  "/login/auth";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
    },
    icon: {
      fontSize: 45,
      [theme.breakpoints.down("xs")]: {
        fontSize: 30,
        marginRight: theme.spacing(3),
      },
    },
    loginGoogleBar: {
      backgroundColor: "#4F8AFF",
      width: 250,
      borderRadius: 2,
      borderWidth: 1,
      color: "white",
      padding: 3,
      textAlign: "center",
      verticalAlign:"center",
      marginLeft: -theme.spacing(1),
      [theme.breakpoints.down("xs")]: {
        height: "1.7em",
        marginLeft: -theme.spacing(3.5),
        fontSize: 14,
        width: 150,
      },
    },
  })
);

const Login = (props) => {
  const classes = useStyles();
  return (
    <React.Fragment>
      <IconButton onClick={() => window.location.assign(getAuthUrl(GOOGLE_PROVIDER))}>
        <Icon className={classes.icon}>
          <img
            src={LoginImg}
            style={{
              position: "relative",
              zIndex: 1,
              width: "100%",
              height: "100%",
            }}
            alt="Login with google"
          />
        </Icon>
        <div className={classes.loginGoogleBar}>
          Login with google
        </div>
      </IconButton>
    </React.Fragment>
  );
};

export default withRouter(Login);
