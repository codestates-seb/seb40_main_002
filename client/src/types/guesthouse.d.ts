export interface Room {
  roomName: string;
  roomExplain: string;
  roomPersonnel: number;
  roomPrice: number;
  roomImage?: string;
}

export interface GuestHouseShort {
  imgSrc: string;
  name: string;
  price: number;
  star: number;
  tags: Array<string>;
  id: number;
}

export interface MyReservation {
  guestHouserName: string;
  guestHouseRoomStart: string;
  guestHouseRoomEnd: string;
}

export interface MyReview {
  guestHouseName: string;
  reviewContent: string;
}
