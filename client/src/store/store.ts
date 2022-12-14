import { configureStore } from '@reduxjs/toolkit';
import reducers from './reducer/rootReducer';

// import storage from 'redux-persist/lib/storage';
// import { persistReducer } from 'redux-persist';

// const persistConfig = {
//   key: 'root',
//   storage,
// };

// const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  // reducer: persistedReducer,
  reducer: reducers,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
