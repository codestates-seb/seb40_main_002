import React, { useState } from 'react';
import ReservationPiker from './ReservationPiker';
import CommonBtn from '../common/CommonBtn/CommonBtn';
type Props = {
  rooms: {
    room: {
      roomName: string;
      roomExplain: string;
      roomPersonnel: number;
      roomPrice: number;
      roomImage: string;
    };
  }[];
};

const GhReservation = ({ rooms }: Props) => {
  const [startDay, setStartDay] = useState<string>('2022-11-20');
  const [endDay, setEndDay] = useState<string>('2022-11-21');
  const [participate, setParticipate] = useState<boolean>(false);
  const handleParticipate = () => {
    setParticipate(!participate);
  };

  return (
    <div>
      <div>숙소 예약</div>
      <div className="flex justify-between items-center">
        <ReservationPiker
          setStartDay={setStartDay}
          setEndDay={setEndDay}
        ></ReservationPiker>
        <div className="w-[370px]  border-[1px] border-black rounded-xl drop-shadow-2xl bg-white text-lg">
          <div className="text-center">
            <form className="mt-[10px]">
              <select name="RoomSelect" className="">
                <option value="none">객실 선택</option>
                {rooms.map((el: any, i: number) => (
                  <option value={el.room.roomName} key={i}>
                    {el.room.roomName}
                  </option>
                ))}
              </select>
            </form>
          </div>
          <div className="w-full">
            <div className="w-full">
              <div className="flex justify-center	items-center mt-[20px] px-[10px] ">
                <div className="rounded-tl-CommentRadius border-y-[1px] border-l-[1px] border-black w-full text-center py-[10px]">
                  {startDay}
                </div>
                <div className="rounded-tr-CommentRadius border-black  border-y-[1px] border-x-[1px] w-full text-center  py-[10px]">
                  {endDay}
                </div>
              </div>
              <div className="flex justify-center	 px-[10px]  text-black">
                <div className="pl-[10px] w-6/12 h-6/12 flex-row text-start rounded-bl-CommentRadius border-b-[1px] border-l-[1px] border-black  text-center py-[10px]">
                  <div className="font-bold">인원</div>
                  <div className="text-base">게스트 1명</div>
                </div>
                <div className="p-[10px] flex-row w-6/12 h-6/12  rounded-br-CommentRadius border-black  border-b-[1px] border-x-[1px] text-center  py-[10px]">
                  <div className="text-left font-bold">파티 참석 여부</div>
                  <div className="flex">
                    <input
                      type="checkbox"
                      className="checked:bg-blue-500"
                      onClick={handleParticipate}
                    />
                    <div className="text-base">파티에 참석합니다.</div>
                  </div>
                </div>
              </div>
            </div>
            <div className="p-[10px] text-black pl-[20px]">
              <div>총 합계</div>
              <div className="flex justify-between mt-[10px]">
                <div>₩166,773 x 1박</div>
                <div className="pr-[20px]">₩166,773</div>
              </div>
            </div>
            <div className="my-[10px] px-[10px]">
              <CommonBtn btnSize={'w-full h-[40px]'} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default GhReservation;
