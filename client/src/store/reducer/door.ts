import { createSlice } from '@reduxjs/toolkit';

export const door = createSlice({
  name: 'door',
  initialState: {
    isOpened: false,
  },
  reducers: {
    visited() {
      return { isOpened: true };
    },
  },
});

export const { visited } = door.actions;
export default door.reducer;
