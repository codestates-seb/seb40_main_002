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
          roomImage: `http://3.37.58.81:8080${el.roomImageUrl}`,
          roomExplain: el.roomInfo,
          reservePossible: el.reservePossible,
        };
        return <RoomInfo room={data} key={i} />;
      })}
    </div>
  );
};
export default RoomsDetail;
