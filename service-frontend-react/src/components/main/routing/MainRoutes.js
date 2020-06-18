import React from "react";
import Users from "../../users/list/Users";
import Products from "../../products/list/Products";
import Login from "../../login/Login";
import Logout from "../../logout/Logout";
import LoginAuth from "../../login/auth/LoginAuth";
import Typography from "@material-ui/core/Typography";
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
              <Typography paragraph>Lorem ipsum dolor sit amet.</Typography>
              <Typography paragraph>
                Consequat mauris nunc congue nisi vitae suscipit.
              </Typography>
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
