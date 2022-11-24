import axios from 'axios';

import { convertURLtoFile } from '../libs/srcToFile';

type Room = {
  reservePossible: boolean;
  roomId: number;
  roomImageUrl: string;
  roomInfo: string;
  roomName: string;
  roomPrice: number;
};

type ReturnType = {
  guestHouseName: string;
  guestHouseTag: string[];
  guestHouseImage: File[];
  guestHouseInfo: string;
  guestHouseDetails: boolean[];
  rooms: Room[];
};

type GetType = {
  guestHouseName: string;
  guestHouseTag: string[];
  guestHouseImage: string[];
  guestHouseInfo: string;
  guestHouseDetails: boolean[];
  rooms: Room[];
};

export const ghEditDatafilter = async (url: string) => {
  const {
    data: { data: returnData },
  } = await axios.get(url);

  const data = returnData as GetType;

  // 게스트하우스 명
  const guestHouseName = data.guestHouseName;

  // 게스트 하우스 태그
  const guestHouseTag = data.guestHouseTag;

  //게스트 하우스 이미지
  const guestHouseImage = await Promise.all(
    data.guestHouseImage.map(async (src: string) => {
      return await convertURLtoFile(src);
    })
  );

  // 게스트 하우스 설명
  const guestHouseInfo = data.guestHouseInfo;

  // 게스트 하우스 편의시설
  const guestHouseDetails = data.guestHouseDetails;

  // 객실 정보
  const rooms = await Promise.all(
    data.rooms.map(async (room: Room) => {
      const imgFile = await convertURLtoFile(room.roomImageUrl);
      return {
        roomName: room.roomName,
        roomExplain: room.roomInfo,
        roomPrice: room.roomPrice,
        roomImage: [imgFile],
        roomId: room.roomId,
      };
    })
  );

  const editData = {
    guestHouseName,
    guestHouseTag,
    guestHouseImage,
    guestHouseInfo,
    guestHouseDetails,
    rooms,
  };

  return editData;
};

// api/guesthouse/{{guesthouse-id}}?start=2022-11-22&end=2022-11-28
