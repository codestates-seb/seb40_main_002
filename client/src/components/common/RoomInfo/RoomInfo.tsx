import Info from './Info';
import { BsPencilSquare, BsFillTrashFill } from 'react-icons/bs';
import { convertURLtoFile } from '../../../libs/srcToFile';

type ReEditData = {
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
interface RoomsProps {
  roomId?: number;
  roomName: string;
  roomPrice: number;
  roomImageUrl: string;
  roomInfo: string;
  reservePossible?: boolean;
}
function RoomInfo({
  room,
  reEdit,
  edit,
  idx,
  removeCard,
}: {
  room: RoomsProps;
  reEdit?: (input: ReEditData) => void;
  removeCard?: (input: RemoveRoom) => void;
  edit?: boolean;
  idx?: number;
}) {
  const changeEditData = async (room: Room, idx?: number) => {
    if (room.roomImage && typeof room.roomImage === 'string') {
      const Files = await convertURLtoFile(room.roomImage);
      // 수정 버튼을 눌렀을 때 제작 모달이 켜지고 값이 입력되어야함

      if (reEdit && idx !== undefined) {
        reEdit({ ...room, roomImage: [Files], idx });
      }
    }
  };

  const remove = (room: Room) => {
    if (removeCard && room.roomImage) {
      removeCard(room);
    }
  };

  return (
    <div className="relative flex flex-col md:flex-row h-fit p-[12px] bg-white shadow-xl rounded-[15px] mb-[20px]">
      <div className="flex ">
        <img
          src={`http://3.37.58.81:8080${room.roomImageUrl}`}
          className="w-[120px] h-[80px] md:w-[160px] md:h-[120px] object-cover mr-[12px] rounded-[15px]"
        />
        <div className="flex flex-col justify-between my-2">
          <Info title={'객실 명'} content={room.roomName} />
          <Info title={'객실 설명'} content={room.roomInfo} />
          {/* <Info title={'수용 인원'} content={room.roomPersonnel} /> */}
          <Info
            title={'가격'}
            content={`${room.roomPrice.toLocaleString()} KRW / 1박`}
          />
        </div>
        {!room.reservePossible && (
          <div className="text-point-color flex justify-center items-center absolute top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]">
            예약이 마감 된 방 입니다.
          </div>
        )}
      </div>
      {edit && (
        <div className="  flex flex-row-reverse justify-between md:ml-auto md:flex-col md:justify-between">
          <BsPencilSquare
            className="cursor-pointer w-5 h-5"
            onClick={() => changeEditData(room, idx)}
          />
          <BsFillTrashFill
            className="cursor-pointer w-5 h-5"
            onClick={() => remove(room)}
          />
        </div>
      )}
    </div>
  );
}

export default RoomInfo;
