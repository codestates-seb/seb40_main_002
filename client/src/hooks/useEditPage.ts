import React, { useState } from 'react';
import FacilitiesArr from '../components/common/FacilitiesArray';

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  idx?: number;
  roomId?: number | null;
};

export default function useEditPage() {
  // 게스트하우스 이름
  const [guestHouseName, setGuestHouseName] = useState('');

  // 게스트하우스 주소
  const [address, setAddress] = useState({
    zipCode: '', // 우편번호
    guestHouseAddress: '', // 건물명 포함
    detailAddress: '', // 상세 주소
    guestHouseLocation: '', // 경위도
  });

  // 게스트 하우스 태그
  const [guestHouseTag, setGuestHouseTag] = useState<string[]>([]);

  // 게스트 하우스 이미지
  const [imgFiles, setImgFiles] = useState<File[]>([]);

  // 게스트 하우스 설명
  const [guestHouseInfo, setGuestHouseInfo] = useState('');

  // 객실 로직
  const [rooms, setRooms] = useState<Room[]>([]);

  // 편의시설 로직
  const [icons, setIcons] = useState(FacilitiesArr());

  return {
    guestHouseName,
    setGuestHouseName,
    address,
    setAddress,
    guestHouseTag,
    setGuestHouseTag,
    imgFiles,
    setImgFiles,
    guestHouseInfo,
    setGuestHouseInfo,
    rooms,
    setRooms,
    icons,
    setIcons,
  };
}
