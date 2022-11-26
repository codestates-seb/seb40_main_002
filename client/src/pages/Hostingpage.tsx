import axios from 'axios';
import React from 'react';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import AddressContainer from '../components/ghEdit/AddressContainer';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Ghname from '../components/ghEdit/Ghname';
import ImageContainer from '../components/ghEdit/ImageContainer';
import RoomEdit from '../components/ghEdit/RoomEdit';
import TagContainer from '../components/ghEdit/TagContainer';
import useEditPage from '../hooks/useEditPage';
import { ghDataCheck, makeGhData } from '../libs/ghDatafunc';
import { ghCreateForm } from '../libs/ghEditCreateForm';

export default function Hostingpage() {
  const {
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
  } = useEditPage();

  const sendData = async () => {
    const flag = ghDataCheck({
      guestHouseName,
      address,
      guestHouseTag,
      imgFiles,
      guestHouseInfo,
      rooms,
    });

    if (flag) return;

    const { guest_house_dto, roomImg, roomDto } = makeGhData({
      guestHouseName,
      address,
      guestHouseTag,
      imgFiles,
      guestHouseInfo,
      rooms,
      icons,
    });

    const formData = ghCreateForm({
      guest_house_dto,
      roomImg,
      roomDto,
      imgFiles,
    });

    try {
      const postSurvey = await axios.post(`/api/auth/guesthouse`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      console.log(postSurvey);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="w-full p-5">
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
        <RoomEdit rooms={rooms} setRooms={setRooms} mode={'create'} />
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
    </div>
  );
}
