import React, { useEffect, useState } from 'react';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import axios from 'axios';
import { getPaymentParams } from '../../libs/getuserParams';
import { getGhData } from '../../api/ghdata';
import dateCheck from '../../libs/dateCheck';
import { useNavigate } from 'react-router-dom';
type ReservationType = {
  guestHouserName: string;
  guestHouseRoomStart: string;
  guestHouseRoomEnd: string;
  guestHouseDays: number;
  guestHousePrice: number;
  guestHouseImg: string;
  guestHouseRoomName: string;
};

interface Room {
  roomId: number;
  roomName: string;
  roomPrice: number;
  roomImageUrl: string;
}

const ReservationInfo = () => {
  const url = new URL(window.location.href);
  const navigate = useNavigate();
  const [startDate, endDate, roomId, guestHouseId] = getPaymentParams(url, [
    'start',
    'end',
    'room',
    'gh',
  ]);

  const [loading, setLoading] = useState(false);

  const [reservationData, setReservationData] = useState<ReservationType>();
  const [price, setPrice] = useState<number | null>(10000);

  useEffect(() => {
    const getData = async () => {
      const data = await getGhData(guestHouseId, startDate, endDate);
      const findRooms = data.rooms.filter(
        (x: Room) => x.roomId === Number(roomId)
      )[0];

      const date = dateCheck([startDate, endDate]);

      setReservationData({
        guestHouserName: data.guestHouseName,
        guestHouseRoomName: findRooms.roomName,
        guestHouseRoomStart: startDate,
        guestHouseRoomEnd: endDate,
        guestHouseDays: Number(date[0]),
        guestHousePrice: Number(findRooms.roomPrice),
        guestHouseImg: findRooms.roomImageUrl,
      });

      setPrice(Number(date[0]) * Number(findRooms.roomPrice));
      setLoading(true);
    };

    getData();
  }, []);

  const handleToMain = () => {
    //메인으로 이동
    navigate('/');
  };

  return (
    <>
      {loading && (
        <div className="flex justify-center items-center h-screen ">
          <div className="items-center lg:p-0 lg:flex lg:max-w-[1150px] lg:h-[310px] sm:p-[40px] sm:p-[80px]">
            <div className="w-full ">
              <img
                className=" rounded-[10px]  bg-center bg-no-repeat bg-cover flex-none lg:w-[1150px] lg:h-[310px]"
                src={`${process.env.REACT_APP_SERVER_URL}${reservationData?.guestHouseImg}`}
              ></img>
            </div>
            <div className="lg:ml-[60px] mt-[24px] text-lg w-full">
              <div className="font-semibold	text-xl ">
                {reservationData && reservationData.guestHouserName}
              </div>
              <div className="font-semibold	text-base ">
                {reservationData && reservationData.guestHouseRoomName}
              </div>
              {/* <div className="text-font-color mt-[20px]">2층 201호 6인실(남성)</div> */}
              <div className="mt-[10px]">
                {`${reservationData && reservationData.guestHouseRoomStart} ~ ${
                  reservationData && reservationData.guestHouseRoomEnd
                }`}
                <div />
                <div>
                  게스트 1명
                  <div />
                  <div className="font-semibold mt-[20px] ">요금 세부 정보</div>
                  <div className="mt-[10px]">
                    {`￦${reservationData && reservationData.guestHousePrice}`}{' '}
                    X {reservationData && reservationData.guestHouseDays} 박
                  </div>
                  <div className="flex justify-between items-center ">
                    <span className="font-semibold mt-[10px]">총 합계</span>
                    <span className="text-base pr-[60px] ">{price}</span>
                  </div>
                  <div className="flex justify-center mt-[15px]">
                    <CommonBtn
                      text={'예약 확인'}
                      btnSize={'w-[235px] h-[47px]'}
                      btnHandler={handleToMain}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ReservationInfo;
