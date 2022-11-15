import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface User {
  id: number;
  name: string;
}

let tempId = 3;

// slice 안에 들어갈 내용들은 매우 심플하고 직관적이다.
// name, initialState, reducers.
export const users = createSlice({
  name: 'users',
  initialState: [
    { id: 1, name: 'User1' },
    { id: 2, name: 'User2' },
  ] as User[], // 필수로 타입 지정 안해도 되지만, 확실히 하기로 한다.
  // dispatch 함수
  reducers: {
    addUser(state, action: PayloadAction<User>) {
      action.payload.id = tempId++;
      // 업데이트 되는 State 를 return 해준다.
      return [...state, action.payload];
    },
  },
});

// 액션과 리듀서를 export 해준다. 이건 그냥 따라하면 된다.
export const { addUser } = users.actions;
export default users.reducer;
