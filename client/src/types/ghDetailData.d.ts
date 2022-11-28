export interface ghDetailProps {
  guestHouseId: number;
  guestHouseName: string;
  memberId: string;
  memberNickname: string;
  memberPhone: string;
  memberImageUrl: null;
  guestHouseLocation: string;
  guestHouseAddress: string[];
  guestHousePhone: string;
  guestHouseStatus: string;
  guestHouseDetails: boolean[];
  guestHouseStar: number;
  guestHouseTag: string[];
  guestHouseImage: string[];
  guestHouseInfo: string;
  rooms: [
    {
      roomId: number;
      roomName: string;
      roomPrice: number;
      roomImageUrl: string;
      roomInfo: string;
      reservePossible: boolean;
    }
  ];
  reviews: [
    {
      reviewId: number;
      comment: string;
      star: number;
      reviewComment: {
        reviewCommentId: number;
        reviewComment: string;
        createdAt: string;
        modifiedAt: string;
        user?: string;
      };
      createdAt: string;
      modifiedAt: string;
      member: {
        memberId: string;
        memberNickname: string;
        memberEmail: string;
        memberPhone: string;
        memberBirth: string;
        memberNationality: string;
        memberRegisterKind: string;
        memberImageUrl: string;
        memberTag?: string[];
      };
    }
  ];
  guestHouseReviewCount: number;
  createdAt: string;
  modifiedAt: string;
}

export interface ReviewProps {
  reviewComment: [
    {
      reviewId: number;
      comment: string;
      star: number;
      reviewComment: {
        reviewCommentId: number;
        reviewComment: string;
        createdAt: string;
        modifiedAt: string;
        user?: string;
      };
      createdAt: string;
      modifiedAt: string;
      member: {
        memberId: string;
        memberNickname: string;
        memberEmail: string;
        memberPhone: string;
        memberBirth: string;
        memberNationality: string;
        memberRegisterKind: string;
        memberImageUrl: string;
        memberTag?: string[];
      };
    }
  ];
}
export interface ReviewPropsDetail {
  reviewId: number;
  comment: string;
  star: number;
  reviewComment: {
    reviewCommentId: number;
    reviewComment: string;
    createdAt: string;
    modifiedAt: string;
    user?: string;
  };
  createdAt: string;
  modifiedAt: string;
  member: {
    memberId: string;
    memberNickname: string;
    memberEmail: string;
    memberPhone: string;
    memberBirth: string;
    memberNationality: string;
    memberRegisterKind: string;
    memberImageUrl: string;
    memberTag?: string[];
  };
}

export interface RoomsProps {
  rooms: {
    roomId: number;
    roomName: string;
    roomPrice: number;
    roomImageUrl: string;
    roomInfo: string;
    reservePossible: boolean;
  }[];
  startDay: any;
  endDay: any;
  dayCal: number;
  setStartDay: Dispatch<SetStateAction<string>>;
  setEndDay: Dispatch<SetStateAction<string>>;
  setDayCal: Dispatch<SetStateAction<number>>;
}
