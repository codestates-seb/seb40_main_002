import axios from 'axios';
import React, { useState } from 'react';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import FacilitiesArr from '../components/common/FacilitiesArray';
import AddressContainer from '../components/ghEdit/AddressContainer';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Ghname from '../components/ghEdit/Ghname';
import ImageContainer from '../components/ghEdit/ImageContainer';
import RoomEdit from '../components/ghEdit/RoomEdit';
import TagContainer from '../components/ghEdit/TagContainer';

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  idx?: number;
};

export default function GhEditpage() {
  // 게스트하우스 이름
  const [guestHouseName, setGuestHouseName] = useState('');

  // 게스트하우스 주소
  const [address, setAddress] = useState({
    zipCode: '', // 우편번호
    guestHouseAddress: '', // 건물명 포함
    guestHouseLocation: '', // 경위도
    detailAddress: '',
  });

  // 게스트 하우스 태그
  const [guestHouseTag, setGuestHouseTag] = useState<string[]>([]);

  // 게스트 하우스 이미지
  const [imgFiles, setImgFiles] = useState<File[]>([]);

  // 게스트 하우스 설명
  const [guestHouseInfo, setGuestHouseInfo] = useState('');

  // 객실 로직
  const [rooms, setRooms] = useState<Room[]>([]);

  // 태그 로직
  const [icons, setIcons] = useState(FacilitiesArr());

  const sendData = async () => {
    const formData = new FormData();

    const guestHouseDetails = icons.map((icon) => icon.checked);

    const guest_house_dto = {
      guestHouseName,
      guestHouseLocation: address.guestHouseLocation,
      guestHouseAddress: `${address.guestHouseAddress} ${address.detailAddress}`,
      guestHousePhone: '010-1234-1234',
      guestHouseTag,
      guestHouseInfo,
      guestHouseDetails,
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

    formData.append('guest-house-dto', JSON.stringify(guest_house_dto));
    formData.append('room-dto', JSON.stringify(roomDto));
    for (const el of imgFiles) {
      formData.append('guestHouseImage', el);
    }
    for (const el of roomImg) {
      formData.append('room-image', el);
    }

    // for (const values of formData.values()) {
    //   console.log(values); // 이미지 객체의 정보
    // }
    // for (const key of formData.keys()) {
    //   console.log(key);
    // }
    try {
      const data = await axios.get(
        '/api/guesthouse/1?start=2022-11-22&end=2022-11-28',
        {
          headers: {
            'ngrok-skip-browser-warning': '111',
          },
        }
      );
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="mt-8 p-3 w-full flex justify-center ">
      <div className="md:max-w-[1120px] w-full flex-col flex gap-y-4 md:gap-y-7">
        <Ghname
          guestHouseName={guestHouseName}
          setGuestHouseName={setGuestHouseName}
        />
        <div className="flex flex-col w-full md:flex-row ">
          <AddressContainer address={address} setAddress={setAddress} />
          <TagContainer
            guestHouseTag={guestHouseTag}
            setGuestHouseTag={setGuestHouseTag}
          />
        </div>
        <ImageContainer imgFiles={imgFiles} setImgFiles={setImgFiles} />
        <GhDescription
          guestHouseInfo={guestHouseInfo}
          setGuestHouseInfo={setGuestHouseInfo}
        />
        <RoomEdit rooms={rooms} setRooms={setRooms} />
        <FacilitiesContainer icons={icons} setIcons={setIcons} />
        <div className="flex justify-center mt-20 mb-20">
          {/* 28 14  */}
          <CommonBtn
            btnSize="w-28 h-14 mr-20 md:mr-30 md:w-28 md:h-14"
            text="작성 취소"
            btnFs="text-lg"
            btnHandler={() => {
              console.log('라우터 달기');
            }}
          />
          <CommonBtn
            btnSize="w-28 h-14 md:w-28 md:h-14"
            btnFs="text-lg"
            text="숙소 등록"
            btnHandler={sendData}
          />
        </div>
      </div>
    </div>
  );
}
