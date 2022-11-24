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
import RoomInfo from '../common/RoomInfo/RoomInfo';
import RoomEditer from './RoomEditer';

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  idx?: number;
};

export interface RemoveRoom {
  roomName: string;
  roomExplain: string;
  roomPrice: number;
  roomImage?: string;
}

type RoomsProps = {
  rooms: Room[];
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>;
};

export default function RoomEdit({ rooms, setRooms }: RoomsProps) {
  // 카드들의 정보들을 배열에다 담음 상위로 올려줄것

  // 인풋 수정을 위한 데이터 관리
  const [input, setInput] = useState<Room>({
    roomName: '',
    roomPrice: 0,
    roomExplain: '',
    roomImage: [],
  });

  const [openEditRoom, setOpenEditRoom] = useState(false);

  const openEditer = () => setOpenEditRoom(!openEditRoom);

  const RoomInfoData = (room: Room) => {
    const dataFilter = {
      roomName: room.roomName,
      roomExplain: room.roomExplain,
      roomPrice: room.roomPrice,
      roomImage: URL.createObjectURL(room.roomImage[0]),
    };
    return dataFilter;
  };

  const reEdit = (input: Room) => {
    setInput({ ...input });
    setOpenEditRoom(true);
  };

  const removeCard = (input: RemoveRoom) => {
    const filter = rooms.filter((room) => room.roomName !== input.roomName);
    setRooms([...filter]);
  };

  return (
    <div className="flex flex-col mb-3 md:mb-5">
      <p className="font-bold text-lg mb-2.5">객실 정보</p>
      <div className="w-full flex  flex-col justify-center items-center bg-border-color rounded-CommentRadius p-3 md:rounded-ImgRadius min-h-[300px] max-h-[500px] md:min-h-[500px]">
        {rooms.length > 0 && (
          <div className="self-start w-full flex flex-col overflow-auto p-2.5 md:p-5">
            {rooms.map((room, idx) => {
              if (room.roomPrice) {
                return (
                  <div key={idx} className="w-full rounded-ImgRadius mb-3">
                    <RoomInfo
                      room={RoomInfoData(room)}
                      reEdit={reEdit}
                      edit={true}
                      idx={idx}
                      removeCard={removeCard}
                    />
                  </div>
                );
              }
            })}
          </div>
        )}
        <div className="w-full p-2.5 md:p-5 flex justify-center">
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
              setRooms={setRooms}
              rooms={rooms}
              input={input}
              setInput={setInput}
            />
          )}
        </div>
      </div>
    </div>
  );
}
