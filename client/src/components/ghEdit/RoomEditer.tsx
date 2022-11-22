import React, { useRef, useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import InputContainer from './InputContainer';

import CommonBtn from '../common/CommonBtn/CommonBtn';

type Room = {
  roomName: string;
  roomPrice: number;
  roomExplain: string;
  roomImage: File[];
  idx?: number;
};

type EditRoom = {
  rooms: Room[];
  openEditRoom: boolean;
  setOpenEditRoom: React.Dispatch<React.SetStateAction<boolean>>;
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>;
  input: Room;
  setInput: React.Dispatch<React.SetStateAction<Room>>;
};

export default function RoomEditer({
  openEditRoom,
  setOpenEditRoom,
  setRooms,
  rooms,
  input,
  setInput,
}: EditRoom) {
  // 상위에서 해당 데이터를 받는 로직이 필요함
  // const [img, setImg] = useState<File[]>([]);

  const imgRef = useRef<HTMLInputElement>(null);

  const openSelectFile = () => {
    if (imgRef.current) {
      imgRef.current.click();
    }
  };

  const changeFunc = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInput({ ...input, [e.target.name]: e.target.value });
  };

  const selectImg = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length <= 1) {
      setInput({ ...input, roomImage: [...Array.from(e.target.files)] });
    } else {
      alert('객실 등록 시 사진은 1장만 등록해주세요');
    }
  };

  const finEdit = () => {
    if (input.roomImage.length === 0) return alert('사진을 등록해주세요');
    if (input.roomName.length <= 0) return alert('숙소명을 등록해주세요');
    if (input.roomExplain.length <= 0) return alert('숙소설명을 등록해주세요');
    if (input.roomPrice <= 0) return alert('금액을 정확히 입력해주세요');
    // 수정 버튼을 누르면 idx가 존재한다.
    if (input.idx !== undefined) {
      const reEditRoom = rooms.map((room, x) => {
        if (x === input.idx) {
          const newData = {
            roomName: input.roomName,
            roomPrice: input.roomPrice,
            roomExplain: input.roomExplain,
            roomImage: input.roomImage,
          };
          return { ...newData };
        } else {
          return room;
        }
      });
      setRooms([...reEditRoom]);
    } else {
      setRooms([...rooms, { ...input }]);
    }

    setInput({
      roomName: '',
      roomExplain: '',
      roomPrice: 0,
      roomImage: [],
    });
    setOpenEditRoom(!openEditRoom);
  };

  const cancleEdit = () => {
    setInput({
      roomName: '',
      roomExplain: '',
      roomPrice: 0,
      roomImage: [],
    });
    setOpenEditRoom(!openEditRoom);
  };

  return (
    <div className="max-h-[200px] overflow-auto my-auto mb-2 bg-white w-full p-2.5 md:p-5 rounded-CommentRadius max-w-[1000px] md:mb-4 md:rounded-ImgRadius">
      <div className="flex flex-col w-full md:flex-row md:justify-between">
        <div className="flex flex-col md:flex-row w-10/12 ">
          {input.roomImage.length > 0 ? (
            <img
              src={URL.createObjectURL(input.roomImage[0])}
              className="w-24 h-16 md:w-40 md:h-32 bg-border-color mr-5  object-fill rounded-ImgRadius flex justify-center items-center"
              onClick={openSelectFile}
            />
          ) : (
            <div
              onClick={openSelectFile}
              className="w-24 h-16 mr-3 bg-border-color rounded-ImgRadius flex justify-center items-center md:w-40 md:h-32"
            >
              <BsPlusLg className="cursor-pointer w-5 h-5 items-center text-font-color md:h-10 md:w-10" />
            </div>
          )}
          <input
            type="file"
            multiple
            className="hidden"
            ref={imgRef}
            accept="image/*"
            onChange={(e) => selectImg(e)}
          />

          <div className="flex flex-col justify-between md:w-10/12">
            <InputContainer
              text={'객실명'}
              name={'roomName'}
              changeFunc={changeFunc}
              value={input.roomName}
              placeholder="객실 이름을 입력해주세요"
              type={'text'}
            />
            <InputContainer
              text={'객실설명'}
              name={'roomExplain'}
              changeFunc={changeFunc}
              value={input.roomExplain}
              placeholder="객실 설명을 입력해주세요"
              type={'text'}
            />
            <InputContainer
              text={'객실가격 / 박'}
              name={'roomPrice'}
              changeFunc={changeFunc}
              value={input.roomPrice}
              placeholder="객실 가격을 입력해주세요"
              type={'number'}
            />
          </div>
        </div>
        <div className="flex flex-row w-full md:w-28 justify-end items-end md:justify-end">
          <CommonBtn
            btnSize="h-8 w-16 mr-3"
            text="취소"
            btnHandler={cancleEdit}
          />
          <CommonBtn btnSize="h-8 w-16 " text="등록" btnHandler={finEdit} />
        </div>
      </div>
    </div>
  );
}
