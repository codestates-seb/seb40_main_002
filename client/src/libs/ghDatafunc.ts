interface Room {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  roomId?: number | null;
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

type DataCheck = {
  guestHouseName: string;
  address: Address;
  guestHouseTag: string[];
  imgFiles: File[];
  guestHouseInfo: string;
  rooms: Room[];
};

interface FormData {
  guestHouseName: string;
  address: Address;
  guestHouseTag: string[];
  imgFiles: File[];
  guestHouseInfo: string;
  rooms: Room[];
  icons: Obj[];
}

const checkLength = <T>(lengthCheck: T, alertList: string) => {
  const type = Array.isArray(lengthCheck) || typeof lengthCheck === 'string';
  if (type) {
    if (lengthCheck.length > 0) {
      return false;
    } else {
      alert(`${alertList}등록해주세요`);
      return true;
    }
  }
};

const ghDataCheck = ({
  guestHouseName,
  address,
  guestHouseTag,
  imgFiles,
  guestHouseInfo,
  rooms,
}: DataCheck) => {
  const checkArr = [
    guestHouseName,
    guestHouseTag,
    imgFiles,
    guestHouseInfo,
    rooms,
  ];
  const alertList = [
    '숙소 이름을 ',
    '태그를 ',
    '숙소 이미지를 ',
    '숙소 설명을 ',
    '객실 정보를 ',
  ];
  for (let i = 0; i < checkArr.length; i++) {
    const flag = checkLength(checkArr[i], alertList[i]);
    if (flag) {
      return flag;
    }
  }
  const flag = checkAddress(address);
  if (flag) {
    alert('주소를 등록해주세요');
  }
  return flag;
};

// 주소 체크
const checkAddress = (address: Address): boolean => {
  const entries = Object.entries(address);
  for (let i = 0; i < entries.length; i++) {
    if (entries[i][1] === '') {
      return true;
    }
  }
  return false;
};

const cityIds = [
  {
    cityId: 2,
    cityName: '조천',
  },
  {
    cityId: 3,
    cityName: '구좌',
  },
  {
    cityId: 4,
    cityName: '우도',
  },
  {
    cityId: 5,
    cityName: '성산',
  },
  {
    cityId: 6,
    cityName: '표선',
  },
  {
    cityId: 7,
    cityName: '남원',
  },
  {
    cityId: 9,
    cityName: '중문',
  },
  {
    cityId: 10,
    cityName: '안덕',
  },
  {
    cityId: 11,
    cityName: '대정',
  },
  {
    cityId: 12,
    cityName: '한경',
  },
  {
    cityId: 13,
    cityName: '한림',
  },
  {
    cityId: 14,
    cityName: '애월',
  },
  {
    cityId: 8,
    cityName: '서귀포시',
  },
  {
    cityId: 1,
    cityName: '제주시',
  },
];

const findCityId = (address: string): number => {
  const filter = cityIds.filter((city) => address.includes(city.cityName));
  return filter[0].cityId;
};

const makeGhData = ({
  guestHouseName,
  address,
  guestHouseTag,
  imgFiles,
  guestHouseInfo,
  rooms,
  icons,
}: FormData) => {
  const guestHouseDetails = icons.map((icon) => icon.checked);
  const cityId = findCityId(address.guestHouseAddress);

  const guest_house_dto = {
    guestHouseName,
    guestHouseLocation: address.guestHouseLocation,
    guestHouseAddress: [
      address.zipCode,
      address.guestHouseAddress,
      address.detailAddress,
    ],
    guestHousePhone: '010-1234-1234',
    guestHouseTag,
    guestHouseInfo,
    guestHouseDetails,
    cityId,
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

const EditGhData = ({
  guestHouseName,
  address,
  guestHouseTag,
  imgFiles,
  guestHouseInfo,
  rooms,
  icons,
}: FormData) => {
  const guestHouseDetails = icons.map((icon) => icon.checked);
  const cityId = findCityId(address.guestHouseAddress);

  const guest_house_dto = {
    guestHouseName,
    guestHouseLocation: address.guestHouseLocation,
    guestHouseAddress: [
      address.zipCode,
      address.guestHouseAddress,
      address.detailAddress,
    ],
    guestHousePhone: '010-1234-1234',
    guestHouseTag,
    guestHouseInfo,
    guestHouseDetails,
    cityId,
  };

  const roomData = rooms.map((room) => {
    if (room.roomId) {
      return {
        roomName: room.roomName,
        roomPrice: room.roomPrice,
        roomInfo: room.roomExplain,
        roomId: room.roomId,
      };
    } else {
      return {
        roomName: room.roomName,
        roomPrice: room.roomPrice,
        roomInfo: room.roomExplain,
        roomId: null,
      };
    }
  });

  const roomImg = rooms
    .filter((room) => room.roomId)
    .map((room) => room.roomImage[0]) as File[] | [];

  const newRoomImage = rooms
    .filter((room) => !room.roomId)
    .map((room) => room.roomImage[0]) as File[] | [];

  const roomDto = {
    roomDto: [...roomData],
  };

  return { guest_house_dto, roomDto, roomImg, newRoomImage };
};

export { checkAddress, ghDataCheck, makeGhData, EditGhData };
