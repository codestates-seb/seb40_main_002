import { Room } from '../../../types/guesthouse';
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

function RoomInfo({
  room,
  reEdit,
  edit,
  idx,
  removeCard,
}: {
  room: Room;
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
    <div className="flex flex-col md:flex-row h-fit p-[12px]">
      <img
        src={room.roomImage}
        className="w-[120px] h-[80px] md:w-[160px] md:h-[120px] object-cover mr-[12px] rounded-[15px]"
      />
      <div className="flex flex-col justify-between my-2">
        <Info title={'객실 명'} content={room.roomName} />
        <Info title={'객실 설명'} content={room.roomExplain} />
        {/* <Info title={'수용 인원'} content={room.roomPersonnel} /> */}
        <Info
          title={'가격'}
          content={`${room.roomPrice.toLocaleString()} KRW / 1박`}
        />
      </div>
      {edit && (
        <div className="self-end">
          <BsPencilSquare
            className="cursor-pointer"
            onClick={() => changeEditData(room, idx)}
          />
          <BsFillTrashFill onClick={() => remove(room)} />
        </div>
      )}
    </div>
  );
}

export default RoomInfo;
