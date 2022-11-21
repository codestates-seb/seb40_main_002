import React, { useRef, useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import InputContainer from './InputContainer';
import { TiDelete } from 'react-icons/ti';
import CommonBtn from '../common/CommonBtn/CommonBtn';

type Room = {
  roomName: string;
  roomDes: string;
  roomPrice: string;
  imgSrc: File[];
};

type EditRoom = {
  openEditRoom: boolean;
  setOpenEditRoom: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function RoomEditer({
  openEditRoom,
  setOpenEditRoom,
}: EditRoom) {
  // 상위에서 해당 데이터를 받는 로직이 필요함
  const [img, setImg] = useState<File[]>([]);

  const imgRef = useRef<HTMLInputElement>(null);

  const [input, setInput] = useState<Room>({
    roomName: '',
    roomDes: '',
    roomPrice: '',
    imgSrc: [],
  });

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
      setImg([...Array.from(e.target.files)]);
    } else {
      alert('객실 등록 시 사진은 1장만 등록해주세요');
    }
  };

  const finEdit = () => {
    if (img.length === 0) return alert('사진을 등록해주세요');
    setInput({ ...input, imgSrc: [...img] });
    setOpenEditRoom(!openEditRoom);
  };

  const cancleEdit = () => {
    setInput({
      roomName: '',
      roomDes: '',
      roomPrice: '',
      imgSrc: [],
    });
    setOpenEditRoom(!openEditRoom);
  };

  return (
    <div className="max-h-[200px] overflow-auto  my-auto mb-2 bg-white w-full p-2.5 md:p-5 rounded-CommentRadius max-w-[1000px] md:mb-4 md:rounded-ImgRadius">
      <div className="flex flex-col  md:flex-row md:justify-between">
        <div className="flex flex-col md:flex-row w-10/12 ">
          {img.length > 0 ? (
            <img
              src={URL.createObjectURL(img[0])}
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
            />
            <InputContainer
              text={'객실설명'}
              name={'roomDes'}
              changeFunc={changeFunc}
              value={input.roomDes}
              placeholder="객실 설명을 입력해주세요"
            />

            <InputContainer
              text={'객실가격 / 박'}
              name={'roomPrice'}
              changeFunc={changeFunc}
              value={input.roomPrice}
              placeholder="객실 가격을 입력해주세요"
            />
          </div>
        </div>
        <div className="flex flex-col items-end justify-between">
          <TiDelete className="w-5 h-5 " onClick={cancleEdit} />
          <CommonBtn btnSize="h-8 w-20" text="등록" btnHandler={finEdit} />
        </div>
      </div>
    </div>
  );
}
