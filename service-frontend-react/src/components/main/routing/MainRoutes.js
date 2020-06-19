import React from "react";
import Users from "../../users/list/Users";
import Products from "../../products/list/Products";
import Login from "../../login/Login";
import Logout from "../../logout/Logout";
import LoginAuth from "../../login/auth/LoginAuth";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Switch, Route } from "react-router-dom";

const MainRoutes = (props) => {
  return (
    <React.Fragment>
      <Switch>
        <Route
          path="/"
          exact
          render={() => (
            <div>
              <Typography paragraph style={{ fontWeight: "bold" }}>
                Welcome to a sample React 'n Spring Boot 2 web page using: side
                by side OAuth2 and Form Login, Thymeleaf, JQuery, Bootstrap,
                Hibernate and H2 database
              </Typography>
              <Button
                href="/admin"
                variant="contained"
                color="primary"
                type="submit">
                Admin panel
              </Button>
            </div>
          )}
        />
        {props.authenticated && <Route path="/users/list" render={Users} />}
        <Route path="/products/list" render={Products} />
        <Route path="/login" exact render={Login} />
        <Route path="/login/auth" exact render={LoginAuth} />
        <Route path="/logout" exact render={Logout} />
      </Switch>
    </React.Fragment>
  );
};

export default MainRoutes;
