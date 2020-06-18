import axios from "axios";

const SERVER_DOMAIN_PORT = "localhost:8080";
const CLIENT_DOMAIN_PORT = "localhost:3000";
const PROTOCOL = "http";
const serverURL = PROTOCOL + "://" + SERVER_DOMAIN_PORT;
export const GOOGLE_PROVIDER = "google";

export const getAuthUrl = (provider) =>
  serverURL +
  "/oauth2/authorize/" +
  provider +
  "?redirect_uri=" +
  PROTOCOL +
  "://" +
  CLIENT_DOMAIN_PORT +
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
