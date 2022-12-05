import { MyCommunity } from './community';
import { MyReservation, MyReview } from './guesthouse';

export interface MyPageUser {
  memberId: string;
  memberNickname: string;
  memberEmail: string;
  memberPhone: string;
  memberImageUrl: string;
  memberTag: string[];
  memberReservation: MyReservation[];
  memberReview: MyReview[];
  memberComunity?: MyCommunity[];
  memberRoles?: string[];
}

export interface User extends MyPageUser {
  memberBirth: string;
  memberNationality: string;
  memberRegisterKind: string;
}

export interface User1 {
  memberId: string;
  memberBirth: string;
  memberEmail: string;
  memberImageFile: File[];
  memberNationality: string;
  memberNickname: string;
  memberPhone: string;
  memberRegisterKind: string;
  memberRoles: string[];
  memberTag: string[];
}

export interface User2 {
  memberId: string;
  memberBirth: string;
  memberEmail: string;
  memberImageUrl: string;
  memberNationality: string;
  memberNickname: string;
  memberPhone: string;
  memberRegisterKind: string;
  memberRoles: string[];
  memberTag: string[];
}

export interface MypageUser2 {
  memberId: string;
  memberNickname: string;
  memberEmail: string;
  memberPhone: string;
  memberImageUrl: string;
  memberTag: string[];
  memberRoles?: string[];
  memberBirth: string;
  memberNationality: string;
  memberRegisterKind: string;
}
