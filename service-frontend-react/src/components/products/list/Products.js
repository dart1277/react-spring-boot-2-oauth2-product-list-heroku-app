import cls from "./products.module.css";
import React, { useEffect, useState } from "react";
import { withRouter, NavLink, Route, Switch } from "react-router-dom";
import { makeStyles, createStyles, Theme } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Paper from "@material-ui/core/Paper";
import DeleteForeverIcon from "@material-ui/icons/DeleteForever";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import { useSelector } from "react-redux";
import { isAuthenticated } from "../../../app/reducers/auth/authSlice";
import { deleteById, getForData } from "../../../app/rest/restUtil";
import ProductForm from "../form/ProductForm";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
      marginTop: 25,
    },
    paper: {
      padding: theme.spacing(2),
      fontSize: "0.9em",
      textAlign: "left",
      marginBottom: "2%",
      marginLeft: "auto",
      marginRight: "auto",
      maxWidth: 800,
    },
    delBtn: { float: "right" },
  })
);

const Products = (props) => {
  const classes = useStyles();
  const authenticated = useSelector(isAuthenticated);
  const [dataState, updateDataState] = useState({ screenData: [] });

  useEffect(() => {
    getForData("/products/list", updateDataState);
  }, []);

  const users = dataState.screenData.map((item, index) => (
    <Paper className={classes.paper} key={item.id}>
      <h2 style={{ display: "inline" }}>Product {index + 1}</h2>
      {authenticated && (
        <IconButton
          color="secondary"
          aria-label="delete"
          component="div"
          className={classes.delBtn}
          onClick={() =>
            deleteById(
              item.id,
              "/products/delete/",
              () => getForData("/products/list", updateDataState),
              updateDataState
            )
          }
        >
          <DeleteForeverIcon />
        </IconButton>
      )}
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}>
          <List>
            <ListItem button>
              <ListItemText primary="Product name" secondary={item.prodName} />
            </ListItem>
            <Divider />
            <ListItem button divider>
              <ListItemText primary="Weight (kg)" secondary={item.weight} />
            </ListItem>
          </List>
        </Grid>
        <Grid item xs={12} md={6}>
          <List>
            <ListItem button>
              <ListItemText primary="Net price" secondary={item.netPrice} />
            </ListItem>
            <Divider />
            <ListItem button divider>
              <ListItemText primary="Gross price" secondary={item.grossPrice} />
            </ListItem>
          </List>
        </Grid>
      </Grid>
    </Paper>
  ));

  return (
    <React.Fragment>
      <Switch>
        <Route
          exact
          path={"/products/list"}
          render={() => (
            <React.Fragment>
              {authenticated && (
                <NavLink to={"/products/list/add"}>
                  <Button variant="contained" color="primary">
                    Add Product
                  </Button>
                </NavLink>
              )}
              <div className={classes.root}>{users}</div>
            </React.Fragment>
          )}
        />
        <Route
          path={"/products/list/add"}
          render={() => (
            <ProductForm
              oldState={dataState}
              updateProductsState={updateDataState}
            />
          )}
        />
      </Switch>
    </React.Fragment>
  );
};

export default withRouter(Products);
