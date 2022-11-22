import { Room } from '../../../types/guesthouse';
import Info from './Info';

function RoomInfo({ room }: { room: Room }) {
  return (
    <div className="flex h-fit p-[12px] shadow-xl rounded-[15px] mb-[20px]">
      <img
        src={room.roomImage}
        className="w-[160px] h-[120px] object-cover mr-[12px] rounded-[15px]"
      />
      <div className="flex flex-col justify-between">
        <Info title={'객실 명'} content={room.roomName} />
        <Info title={'객실 설명'} content={room.roomExplain} />
        <Info title={'수용 인원'} content={room.roomPersonnel} />
        <Info
          title={'가격'}
          content={`${room.roomPrice.toLocaleString()} KRW / 1박`}
        />
      </div>
    </div>
  );
}

export default RoomInfo;
