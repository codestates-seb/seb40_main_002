export interface Room {
  roomName: string;
  roomExplain: string;
  roomPersonnel?: number;
  edit?: boolean;
  reEdit?: (room: Room) => void;
  roomPrice: number;
  roomImage?: string;
}

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
};

export interface GuestHouseShort {
  imgSrc: string;
  name: string;
  price: number;
  star: number;
  tags: Array<string>;
  id: number;
}
