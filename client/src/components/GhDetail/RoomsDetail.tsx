import RoomInfo from '../common/RoomInfo/RoomInfo';

interface RoomsProps {
  rooms: {
    room: {
      roomName: string;
      roomExplain: string;
      roomPersonnel: number;
      roomPrice: number;
      roomImage: string;
    };
  }[];
}
const RoomsDetail = ({ rooms }: RoomsProps) => {
  return (
    <div className="border-b-[2px] mb-[20px]">
      <div className="my-[20px]">객실정보</div>
      {rooms.map((el, i) => (
        <RoomInfo {...el} key={i} />
      ))}
    </div>
  );
};
export default RoomsDetail;
