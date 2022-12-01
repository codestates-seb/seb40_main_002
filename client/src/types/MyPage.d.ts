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
