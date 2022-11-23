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
  memberComunity: MyCommunity[];
}

export interface User extends MyPageUser {
  memberBirth: string;
  memberNationality: string;
  memberRegisterKind: string;
}
