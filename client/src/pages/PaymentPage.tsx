import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import Api from '../api2';
import { getGhData } from '../api2/ghdata';
import { ghEditDatafilter } from '../apis/ghEditDatafilter';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';

import PaymentLeft from '../components/paymentPage/PaymentLeft';
import PaymentRight from '../components/paymentPage/PaymentRight';
import dateCheck from '../libs/dateCheck';
import { getPaymentParams } from '../libs/getuserParams';
import { RootState } from '../store/store';
import { Props } from '../types/payment';
interface Room {
  roomId: number;
  roomName: string;
  roomPrice: number;
  roomImageUrl: string;
}

interface ServerData {
  guestHouseId: number;
  guestHouseImage: string[];
  rooms: Room[];
  guestHouseName: string;
  guestHouseReviewCount: number;
  guestHouseStar: number;
}
export default function PaymentPage() {
  const url = new URL(window.location.href);
  const [startDate, endDate, roomId, guestHouseId] = getPaymentParams(url, [
    'start',
    'end',
    'room',
    'gh',
  ]);
  const [loading, setLoading] = useState(false);
  const [paymentRole, setPaymentRole] = useState<string>('카카오페이');
  const navigate = useNavigate();
  const user = useSelector((state: RootState) => state.user);
  const [ghData, setGhdata] = useState<Props>({
    date: [],
    ghname: null,
    payMoney: null,
    url: null,
    reply: null,
    ratedScore: null,
    filterDate: [],
    totalMoney: null,
    ghRoomname: null,
  });

  useEffect(() => {
    if (!user.memberId) {
      alert('로그인을 확인해주세요');
      return navigate('/');
    }
    const reqData = async () => {
      const data = await getGhData(guestHouseId, startDate, endDate);
      const findRooms = data.rooms.filter(
        (x: Room) => x.roomId === Number(roomId)
      )[0];
      const date = dateCheck([startDate, endDate]);

      if (findRooms.reservePossible) {
        //룸이 예약가능한 상태면
        setGhdata({
          date: [startDate, endDate],
          ghname: data.guestHouseName,
          ghRoomname: findRooms.roomName,
          payMoney: findRooms.roomPrice,
          url: `${process.env.REACT_APP_SERVER_URL}${data.guestHouseImage[0]}`,
          reply: [...data.reviews],
          ratedScore: data.guestHouseStar,
          filterDate: [...date],
          totalMoney: Number(date[0]) * Number(findRooms.roomPrice),
        });
        setLoading(true);
      } else if (!findRooms.reservePossible) {
        alert('예약이 불가능한 객실입니다');
        navigate(-1);
      } else {
        alert('로그인 사용자만 사용할 수 있는 서비스 입니다');
        navigate(-1);
      }
    };
    reqData();
  }, []);

  const reservationHandler = async () => {
    if (paymentRole === '현금결제') return alert('준비 중입니다.');
    const sendBody = {
      roomReservationStart: startDate,
      roomReservationEnd: endDate,
    };
    try {
      const sendData = await Api.post(
        `/api/auth/guesthouse/${guestHouseId}/room/${roomId}/reservation`,
        sendBody
      );
      const navigateData = sendData.data.data;

      navigate(
        `/reservation?start=${startDate}&end=${endDate}&room=${roomId}&gh=${guestHouseId}`
      );
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      {loading && (
        <div
          className={`flex flex-col h-full mt-20 w-full justify-center items-center p-[10px] 
      md:justify-center md:items-center mt-20`}
          // 최소 764이상일때 sm들이 실행됨
        >
          <div className="flex flex-col  w-full md:flex-row md:justify-center md:items-end">
            <PaymentLeft
              ghData={ghData}
              paymentRole={paymentRole}
              setPaymentRole={setPaymentRole}
            />
            <PaymentRight ghData={ghData} />
          </div>
          <div className="w-full justify-center flex">
            <CommonBtn
              btnSize="mt-[30px] self-start mb-20 w-[200px] h-[60px] md:w-[270px] md:h-[55px] md:mt-[70px] "
              text="계속"
              btnFs="text-lg"
              btnHandler={reservationHandler}
            />
          </div>
        </div>
      )}
    </>
  );
}
