import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    authenticated: false,
  },
  reducers: {
    logIn: (state) => {
      state.authenticated = true;
    },
    logOut: (state) => {
      state.authenticated = false;
    },
  },
});

export const { logIn, logOut } = authSlice.actions;

export const isAuthenticated = (state) => state.auth.authenticated;

export default authSlice.reducer;
