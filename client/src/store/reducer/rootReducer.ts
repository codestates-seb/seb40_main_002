import { combineReducers } from '@reduxjs/toolkit';
import user from './user';
import door from './door';

// 만들어 놓은 리듀서들을 합친다.
export const reducers = combineReducers({
  user,
  door,
});

// React에서 사용할 수 있도록 타입을 만들어 export 해준다.
export type ReducerType = ReturnType<typeof reducers>;
export default reducers;
