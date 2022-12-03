import axios from 'axios';
import React, { useEffect, useState } from 'react';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import AddressContainer from '../components/ghEdit/AddressContainer';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Ghname from '../components/ghEdit/Ghname';
import ImageContainer from '../components/ghEdit/ImageContainer';
import RoomEdit from '../components/ghEdit/RoomEdit';
import TagContainer from '../components/ghEdit/TagContainer';
import { ghEditDatafilter } from '../apis/ghEditDatafilter';
import { ghDataCheck, EditGhData } from '../libs/ghDatafunc';
import { useNavigate, useParams } from 'react-router-dom';
import { ghEditForm } from '../libs/ghEditCreateForm';
import useEditPage from '../hooks/useEditPage';
import Api from '../api2';
import { getUser } from '../api2/member';
import { User2 } from '../types/user';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
// 편집 페이지
export default function GhEditPage2() {
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(false);
  const mainUser = useSelector((state: RootState) => state.user);
  // 게스트하우스 이름
  const navigate = useNavigate();
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

  useEffect(() => {
    const asynData = async () => {
      try {
        const userGet = (await getUser()) as User2;
        if (
          userGet.memberRoles !== undefined &&
          userGet.memberRoles.length > 0
        ) {
          if (userGet.memberRoles[0] !== 'ADMIN') {
            alert('호스트가 아닙니다.');
            navigate('/');
          }
        } else {
          alert('호스트가 아닙니다');
          navigate('/');
        }

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
      } catch (e) {
        alert('로그인 상태를 확인해주세요');
        navigate('/');
        localStorage.removeItem('refreshToken');
        sessionStorage.removeItem('persist:root');
        localStorage.removeItem('accessToken');
        window.location.reload();
      }
    };
    asynData();
  }, [id]);

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
    const userPhone = mainUser.memberPhone;
    const { guest_house_dto, roomDto, roomImg, newRoomImage } = EditGhData({
      guestHouseName,
      address,
      guestHouseTag,
      imgFiles,
      guestHouseInfo,
      rooms,
      icons,
      userPhone,
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
          headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: `${localStorage.getItem('accessToken')}`,
          },
        }
      );
      navigate('/ghadmin');
      window.location.reload();
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
