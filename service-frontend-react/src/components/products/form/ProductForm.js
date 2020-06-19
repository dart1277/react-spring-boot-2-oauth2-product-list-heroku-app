import React, { useCallback } from "react";
import { withRouter } from "react-router-dom";
import { useForm } from "react-hook-form";
import Paper from "@material-ui/core/Paper";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { postData } from "../../../app/rest/restUtil";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
      justifyContent: "center",
      flexDirection: "column",
      alignItems: "center",
      maxWidth: 800,
      marginLeft: "auto",
      marginRight: "auto",
    },
    icon: {
      fontSize: 45,
      [theme.breakpoints.down("xs")]: {
        fontSize: 30,
        marginRight: theme.spacing(3),
      },
    },
    header: {
      fontWeight: "bold",
      fontFamily: "Arial",
      fontSize: 18,
      margin: 10,
    },
    input: {
      maxWidth: 250,
      margin: 15,
    },
  })
);

const ProductForm = (props) => {
  const classes = useStyles();
  const onSubmit = useCallback(
    (data) => {
      postData(
        "/products/add",
        data,
        props.oldState,
        props.updateProductsState,
        () => props.history.replace('/products/list')
      );
    },
    [props.oldState, props.updateProductsState, props.history]
  );

  const { register, handleSubmit, watch, errors } = useForm();
  return (
    <React.Fragment>
      <Paper className={classes.root}>
        <div className={classes.header}>Add product</div>
        <TextField
          required
          id="prodName"
          name="prodName"
          label="Product Name"
          className={classes.input}
          variant="outlined"
          error={!!errors.prodName}
          inputRef={register({ required: true, maxLength: 250 })}
        />
        {errors.prodName && "Product name length invalid"}
        <TextField
          required
          id="weight"
          name="weight"
          label="Weight"
          type="number"
          InputLabelProps={{
            shrink: true,
          }}
          className={classes.input}
          variant="outlined"
          error={!!errors.weight}
          inputRef={register({ required: true, min: 0, max: 150 })}
        />
        {errors.weight && "Weight value invalid"}
        <TextField
          required
          id="grossPrice"
          name="grossPrice"
          label="Gross price"
          type="number"
          InputLabelProps={{
            shrink: true,
          }}
          className={classes.input}
          variant="outlined"
          error={!!errors.grossPrice}
          inputRef={register({ required: true, min: 0.01 })}
        />
        {errors.grossPrice && "Gross price value invalid"}
        <TextField
          required
          id="netPrice"
          name="netPrice"
          label="Net price"
          type="number"
          InputLabelProps={{
            shrink: true,
          }}
          className={classes.input}
          variant="outlined"
          error={!!errors.netPrice}
          inputRef={register({ required: true, min: 0.01 })}
        />
        {errors.netPrice && "Net price value invalid"}
        <Button
          variant="contained"
          color="primary"
          type="submit"
          className={classes.input}
          onClick={handleSubmit(onSubmit)}
        >
          Submit
        </Button>
      </Paper>
    </React.Fragment>
  );
};

export default withRouter(ProductForm);
