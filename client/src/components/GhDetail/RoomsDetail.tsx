import RoomInfo from '../common/RoomInfo/RoomInfo';

interface RoomsProps {
  rooms: {
    roomId: number;
    roomName: string;
    roomPrice: number;
    roomImageUrl: string;
    roomInfo: string;
    reservePossible: boolean;
  }[];
}
const RoomsDetail = ({ rooms }: RoomsProps) => {
  return (
    <div className="border-b-[2px] mb-[20px]">
      <div className="my-[20px]">객실정보</div>
      {rooms.map((el, i) => {
        const data = {
          roomName: el.roomName,
          roomPrice: el.roomPrice,
          roomImage: `${process.env.REACT_APP_SERVER_URL}${el.roomImageUrl}`,
          roomExplain: el.roomInfo,
          reservePossible: el.reservePossible,
        };
        return (
          <div className="relative" key={i}>
            {!el.reservePossible && (
              <div className="flex justify-center items-center absolute	rounded-[15px] top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]">
                에약이 완료 된 객실입니다
              </div>
            )}
            <RoomInfo room={data} />
          </div>
        );
      })}
    </div>
  );
};
export default RoomsDetail;
