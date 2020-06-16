import cls from "./users.module.css";
import React from "react";
import { withRouter } from "react-router-dom";
import { makeStyles, createStyles, Theme } from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Paper from "@material-ui/core/Paper";
import DeleteForeverIcon from "@material-ui/icons/DeleteForever";
import IconButton from "@material-ui/core/IconButton";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
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

const onDeleteUsr = (event, idx) => {
  console.log(event.target + " " + idx);
};

const Users = (props) => {
  const classes = useStyles();
  const usrData = [
    {
      id: 123120,
      firstName: "fn",
      lastName: "ln",
      birthDate: "1998",
      phoneNo: "555123456",
    },
    {
      id: 123121,
      firstName: "fn1",
      lastName: "ln1",
      birthDate: "1999",
      phoneNo: "555123457",
    },
  ];
  const users = usrData.map((item, index) => (
    <Paper className={classes.paper} key={item.id}>
      <h2 style={{ display: "inline" }}>User {index + 1}</h2>
      <IconButton
        color="secondary"
        aria-label="delete"
        component="div"
        className={classes.delBtn}
        onClick={(event) => onDeleteUsr(event, item.id)}
      >
        <DeleteForeverIcon />
      </IconButton>
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}>
          <List>
            <ListItem button>
              <ListItemText primary="First name" secondary={item.firstName} />
            </ListItem>
            <Divider />
            <ListItem button divider>
              <ListItemText primary="Last name" secondary={item.lastName} />
            </ListItem>
          </List>
        </Grid>
        <Grid item xs={12} md={6}>
          <List>
            <ListItem button>
              <ListItemText primary="Birth date" secondary={item.birthDate} />
            </ListItem>
            <Divider />
            <ListItem button divider>
              <ListItemText primary="Phone" secondary={item.phoneNo} />
            </ListItem>
          </List>
        </Grid>
      </Grid>
    </Paper>
  ));

  return <div className={classes.root}>{users}</div>;
};

export default withRouter(Users);
