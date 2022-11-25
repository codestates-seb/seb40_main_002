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
      {rooms.map((el, i) => (
        <RoomInfo room={el} key={i} />
      ))}
    </div>
  );
};
export default RoomsDetail;
