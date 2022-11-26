import axios from 'axios';
import React, { useEffect, useState } from 'react';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import FacilitiesArr from '../components/common/FacilitiesArray';
import AddressContainer from '../components/ghEdit/AddressContainer';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Ghname from '../components/ghEdit/Ghname';
import ImageContainer from '../components/ghEdit/ImageContainer';
import RoomEdit from '../components/ghEdit/RoomEdit';
import TagContainer from '../components/ghEdit/TagContainer';
import { ghEditDatafilter } from '../apis/ghEditDatafilter';
import {
  checkName,
  checkAddress,
  checkTag,
  checkInfo,
  checkRooms,
  checkGhimages,
  EditGhData,
} from '../libs/ghDatafunc';
import { useParams } from 'react-router-dom';
import { ghEditForm } from '../libs/ghEditCreateForm';

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  idx?: number;
  roomId?: number | null;
};

// 편집 페이지
export default function GhEditPage2() {
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(false);
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

  useEffect(() => {
    const asynData = async () => {
      const {
        guestHouseName,
        guestHouseTag,
        guestHouseImage,
        guestHouseInfo,
        guestHouseDetails,
        rooms,
        guestHouseAddress,
      } = await ghEditDatafilter(
        `/api/guesthouse/${id}?start=2022-11-22&end=2022-11-28`
      );
      setAddress({ ...guestHouseAddress });
      setGuestHouseName(guestHouseName);
      setGuestHouseTag(guestHouseTag);
      setGuestHouseInfo(guestHouseInfo);
      setImgFiles(guestHouseImage);
      setIcons((prev) => {
        const detailSetting = prev.map((facility, idx) => {
          return { ...facility, checked: guestHouseDetails[idx] };
        });
        return [...detailSetting];
      });
      setRooms([...rooms]);
      setIsLoading(true);
    };
    asynData();
  }, [id]);

  const sendData = async () => {
    // 함수에다 몰아넣고 {return true}를 줘서 해당 값에 의해 함수가 끊기게
    if (checkName(guestHouseName))
      return alert('게스트 하우스명을 작성해주세요');
    if (checkAddress(address)) return alert('주소를 작성해주세요');
    if (checkTag(guestHouseTag)) return alert('태그를 선택해주세요');
    if (checkGhimages(imgFiles)) return alert('이미지를 등록해주세요');
    if (checkInfo(guestHouseInfo))
      return alert('게스트하우스 설명을 작성해주세요');
    if (checkRooms(rooms)) return alert('태그를 선택해주세요');

    const { guest_house_dto, roomDto, roomImg, newRoomImage } = EditGhData({
      guestHouseName,
      address,
      guestHouseTag,
      imgFiles,
      guestHouseInfo,
      rooms,
      icons,
    });

    const formData = ghEditForm({
      guest_house_dto,
      roomImg,
      newRoomImage,
      roomDto,
      imgFiles,
    });

    try {
      const postSurvey = await axios.put(
        `/api/auth/guesthouse/${id}`,
        formData,
        {
          headers: { 'Content-Type': 'multipart/form-data' },
        }
      );
      console.log(postSurvey);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="w-full p-5">
      {isLoading && (
        <div className="w-full flex-col self-center flex gap-y-4 md:gap-y-7">
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
          <RoomEdit rooms={rooms} setRooms={setRooms} mode={'edit'} />
          <FacilitiesContainer icons={icons} setIcons={setIcons} />
          <div className="flex justify-center mt-5 md:mt-10 mb-20">
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
      )}
    </div>
  );
}
