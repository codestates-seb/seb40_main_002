import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { User } from '../../types/user';

export const user = createSlice({
  name: 'user',
  initialState: {
    memberId: '',
    memberNickname: '',
    memberEmail: '',
    memberPhone: '',
    memberImageUrl: '',
    memberTag: [],
    memberReservation: [],
    memberReview: [],
    memberRoles: [],
    memberBirth: '',
    memberNationality: '',
    memberRegisterKind: '',
  } as User, // 필수로 타입 지정 안해도 되지만, 확실히 하기로 한다.
  // dispatch 함수
  reducers: {
    setUser(state, action: PayloadAction<User>) {
      // action.payload.id = tempId++;
      const myUser: User = {
        memberId: action.payload.memberId,
        memberNickname: action.payload.memberNickname,
        memberEmail: action.payload.memberEmail,
        memberPhone: action.payload.memberPhone,
        memberImageUrl: action.payload.memberImageUrl,
        memberTag: action.payload.memberTag,
        memberReservation: action.payload.memberReservation,
        memberReview: action.payload.memberReview,
        memberRoles: action.payload.memberRoles,
        memberBirth: action.payload.memberBirth,
        memberNationality: action.payload.memberNationality,
        memberRegisterKind: action.payload.memberRegisterKind,
      };

      // 업데이트 되는 State 를 return 해준다.
      return myUser;
    },
    clearUser(state, action: PayloadAction) {
      const userClear: User = {
        memberId: '',
        memberNickname: '',
        memberEmail: '',
        memberPhone: '',
        memberImageUrl: '',
        memberTag: [],
        memberReservation: [],
        memberReview: [],
        memberRoles: [],
        memberBirth: '',
        memberNationality: '',
        memberRegisterKind: '',
      };
      return userClear;
    },
  },
});

// 액션과 리듀서를 export 해준다. 이건 그냥 따라하면 된다.
export const { setUser, clearUser } = user.actions;
export default user.reducer;
