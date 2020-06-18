import React, { useEffect } from "react";
import { withRouter } from "react-router-dom";
import { useDispatch } from "react-redux";
import { logIn, logOut } from "../../../app/reducers/auth/authSlice";

const LoginAuth = (props) => {
  const dispatch = useDispatch();
  useEffect(() => {
    const match = window.location.search.match(/token=(.+)/);
    if (match && match.length === 2) {
      localStorage.setItem("token", match[1]);
      props.history.replace("/users/list");
      dispatch(logIn());
    } else {
      dispatch(logOut());
      props.history.replace("/");
    }
  });
  return <React.Fragment>Authenticating...</React.Fragment>;
};

export default withRouter(LoginAuth);
