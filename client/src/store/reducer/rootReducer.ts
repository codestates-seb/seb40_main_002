import { combineReducers } from '@reduxjs/toolkit';
import user from './user';
import door from './door';

// import storage from 'redux-persist/lib/storage';
import sessionStorage from 'redux-persist/lib/storage/session';
import { persistReducer } from 'redux-persist';

// const persistConfig = {
//   key: 'root',
//   storage,
// };

const doorPersistConfig = {
  key: 'door',
  storage: sessionStorage,
};

// 만들어 놓은 리듀서들을 합친다.
export const reducers = combineReducers({
  // user: persistReducer(persistConfig, user),
  user,
  door: persistReducer(doorPersistConfig, door),
});

// React에서 사용할 수 있도록 타입을 만들어 export 해준다.
export type ReducerType = ReturnType<typeof reducers>;
export default reducers;
