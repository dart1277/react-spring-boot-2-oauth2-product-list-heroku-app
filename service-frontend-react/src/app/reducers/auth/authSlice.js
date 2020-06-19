import { createSlice } from "@reduxjs/toolkit";

// Redux Toolkit allows us to write "mutating" logic in reducers. It
// doesn't actually mutate the state because it uses the Immer library,
// which detects changes to a "draft state" and produces a brand new
// immutable state based off those changes

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
