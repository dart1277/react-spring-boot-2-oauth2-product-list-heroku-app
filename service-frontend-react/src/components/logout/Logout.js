import React, { useEffect } from "react";
import { withRouter } from "react-router-dom";
import { useDispatch } from "react-redux";
import { logOut } from "../../app/reducers/auth/authSlice";

const Logout = (props) => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(logOut());
    props.history.replace("/");
  });
  return <React.Fragment>Logging out...</React.Fragment>;
};

export default withRouter(Logout);
