import React, { useState } from 'react';
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
      </div>
    </div>
  );
}
