interface Room {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  roomId?: number;
  idx?: number;
}

interface Obj {
  icon: JSX.Element;
  name: string;
  checked: boolean;
}

interface Address {
  [key: string]: string;
}

interface FormData {
  guestHouseName: string;
  address: Address;
  guestHouseTag: string[];
  imgFiles: File[];
  guestHouseInfo: string;
  rooms: Room[];
  icons: Obj[];
}

// 게스트하우스 명
const checkName = (guestHouseName: string) =>
  guestHouseName.length > 0 ? false : true;

// 주소 체크
const checkAddress = (address: Address): boolean => {
  const entries = Object.entries(address);
  for (let i = 0; i < entries.length; i++) {
    if (entries[i][0] !== 'detailAddress') {
      if (entries[i][1] === '') {
        return true;
      }
    }
  }
  return false;
};

// 하우스 태그
const checkTag = (tags: string[]) => (tags.length > 0 ? false : true);

//하우스 설명
const checkInfo = (guestHouseInfo: string) =>
  guestHouseInfo.length > 0 ? false : true;

//객실
const checkRooms = (rooms: Room[]) => (rooms.length > 0 ? false : true);

// 게스트 하우스 이미지
const checkGhimages = (imgs: File[]) => (imgs.length > 0 ? false : true);

const makeFormData = ({
  guestHouseName,
  address,
  guestHouseTag,
  imgFiles,
  guestHouseInfo,
  rooms,
  icons,
}: FormData) => {
  const guestHouseDetails = icons.map((icon) => icon.checked);

  const guest_house_dto = {
    guestHouseName,
    guestHouseLocation: address.guestHouseLocation,
    guestHouseAddress: `${address.guestHouseAddress} ${address.detailAddress}`,
    guestHousePhone: '010-1234-1234',
    guestHouseTag,
    guestHouseInfo,
    guestHouseDetails,
    cityId: 1,
  };

  const roomData = rooms.map((room) => {
    return {
      roomName: room.roomName,
      roomPrice: room.roomPrice,
      roomInfo: room.roomExplain,
    };
  });

  const roomImg = rooms.map((room) => {
    return room.roomImage[0];
  });

  const roomDto = {
    roomDto: [...roomData],
  };
  return { guest_house_dto, roomDto, roomImg };
};

export {
  checkAddress,
  checkName,
  checkInfo,
  checkRooms,
  checkTag,
  checkGhimages,
  makeFormData,
};
