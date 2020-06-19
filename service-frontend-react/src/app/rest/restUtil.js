import axios from "axios";
import {
  SERVER_DOMAIN_AND_PORT,
  CLIENT_DOMAIN_AND_PORT,
  PROTOCOL
} from "../../config/config";


const serverURL = PROTOCOL + "://" + SERVER_DOMAIN_AND_PORT;
export const GOOGLE_PROVIDER = "google";

export const getAuthUrl = (provider) =>
  serverURL +
  "/oauth2/authorize/" +
  provider +
  "?redirect_uri=" +
  PROTOCOL +
  "://" +
  CLIENT_DOMAIN_AND_PORT +
  "/login/auth";

export const getReqConfig = (method) => {
  const token = localStorage.getItem("token");
  let reqConfig = {};
  if (token != null) {
    reqConfig = {
      crossDomain: true,
      headers: {
        Authorization: "Bearer " + token,
      },
    };
  }
  return reqConfig;
};

export const deleteById = (id, uri, thenFunc, updateDataState) => {
  axios
    .delete(serverURL + uri + id, getReqConfig())
    .then(() => {
      thenFunc();
    })
    .catch((err) => {
      updateDataState({ screenData: [] });
      console.log(err);
    });
};

export const getForData = (uri, updateDataState) => {
  axios
    .get(serverURL + uri, getReqConfig())
    .then((data) => {
      updateDataState({ screenData: data.data });
      console.log(data);
    })
    .catch((err) => {
      updateDataState({ screenData: [] });
      console.log(err);
    });
};

export const postData = (
  uri,
  newData,
  oldStateData,
  updateDataState,
  redirectCallback
) => {
  axios
    .post(serverURL + uri, newData, getReqConfig())
    .then((data) => {
      updateDataState({
        screenData: oldStateData.screenData.concat(data.data),
      });
      console.log(data);
      if (redirectCallback != null) {
        redirectCallback();
      }
    })
    .catch((err) => {
      updateDataState({ screenData: [].concat(oldStateData.screenData) });
      console.log(err);
    });
};
