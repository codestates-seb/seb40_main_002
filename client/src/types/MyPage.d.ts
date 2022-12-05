export interface HeartDataType {
  heartId: number;
  guestHouse: {
    guestHouseId: number;
    guestHouseName: string;
    guestHouseStar: number;
    guestHouseTag: string[];
    mainImageUrl: string;
    minRoomPrice: number;
  };
  createdAt: string;
  modifiedAt: string;
}
export interface DataType {
  data: HeartDataType[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface Reviews {
  data: [
    {
      reviewId: number;
      guestHouse: {
        guestHouseId: number;
        guestHouseName: string;
      };
      roomReservation: {
        room: {
          roomId: number;
          roomName: string;
          roomPrice: number;
          roomImageUrl: string;
          roomInfo: string;
          reservePossible: boolean;
        };
        roomReservationStart: string;
        roomReservationEnd: string;
      };
      comment: string;
      star: number;
      createdAt: string;
      modifiedAt: string;
    }
  ];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface ReservationType {
  data: {
    guestHouseId: number;
    guestHouseName: string;
    roomName: string;
    roomImageUrl: string;
    roomReservationStart: string;
    roomReservationEnd: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}
