// [
// {"roomName": "방이름수정", "roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3},
// {"roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3,imgInfo:"{file : fils[0]}"},
// {"roomName": "방이름수정", {"roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3}]
// []
// [{"roomName": "방이름수정", "roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3}]
// [{"roomName": "방이름수정", "roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3}]
// [{"roomName": "방이름수정", "roomPrice":60000,"roomInfo": "룸정보수정", "roomCapacity":3}]
//  [방1 ,방2, 방3 ] => [{방1},{방3}]
//  [방1 ,방2, 방3 ] => [{방1},{방3}] 방2 서버에서 남아있나요?

import React, { useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import RoomEditer from './RoomEditer';

type Room = {
  roomName: string;
  roomPrice: number;
  roomInfo: string;
};

export default function RoomEdit() {
  // 카드들의 정보들을 배열에다 담음 상위로 올려줄것
  const [rooms, setRooms] = useState<Room[]>([]);
  const [openEditRoom, setOpenEditRoom] = useState(false);

  const openEditer = () => setOpenEditRoom(!openEditRoom);
  return (
    <div className="flex flex-col mb-3 md:mb-5">
      <p className="font-bold text-lg mb-2.5">객실 정보</p>
      <div className="w-full flex flex-col overflow-auto justify-center items-center bg-border-color rounded-CommentRadius p-3 h-60 md:rounded-ImgRadius md:min-h-[500px]">
        {rooms.length > 0 && (
          <div className="self-start flex flex-col  p-2.5 md:p-5">
            {/* 등록된 카드 넣기 */}
          </div>
        )}
        {!openEditRoom && rooms.length === 0 && (
          <BsPlusLg
            onClick={openEditer}
            className="cursor-pointer w-7 h-7 items-center text-font-color md:h-16 md:w-16"
          />
        )}
        {!openEditRoom && rooms.length > 0 && (
          <CommonBtn
            btnSize="w-36 h-7 mb-2 md:mb-4 my-auto self-center bg-border-color border-font-color border-2 text-font-color md:w-52 md:h-12 delay-150 transition ease-in-out duration-500 hover:bg-point-color hover:text-white hover:border-point-color"
            btnFs="text-sm md:text-base"
            text={'숙소 추가하기'}
            btnHandler={openEditer}
          />
        )}
        {openEditRoom && (
          <RoomEditer
            openEditRoom={openEditRoom}
            setOpenEditRoom={setOpenEditRoom}
          />
        )}
      </div>
    </div>
  );
}
