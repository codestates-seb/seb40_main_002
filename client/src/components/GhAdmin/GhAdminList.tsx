import { BsPlusLg } from 'react-icons/bs';
import { useNavigate } from 'react-router-dom';
import GuesthouseCard from '../common/GuesthouseCard';

interface Props {
  ghAdminData: {
    memberNickname: string;
    guestHouseImage: string[];
    guestHouseName: string;
    guestHouseStar: number;
    guestHouseTag: Array<string>;
    guestHouseId: number;
    rooms: { roomPrice: number }[];
  }[];
}
const GhAdminList = ({ ghAdminData }: Props) => {
  const navigate = useNavigate();
  console.log(ghAdminData);
  const addGhHandler = () => {
    //useNavigate 사용 예정
    navigate('/ghedit');
  };
  return (
    <div className="h-full overflow-auto">
      <div className="text-center text-xl m-[20px] ">
        <span className=" font-bold">{ghAdminData[0]?.memberNickname}</span>
        님의 숙소 리스트
      </div>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-[20px] w-full h-full">
        <div className="w-full h-full">
          <div
            onClick={addGhHandler}
            className="aspect-[27/25] relative  bg-zinc-300 rounded-[15px] cursor-pointer"
          >
            <div className=" absolute top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%]">
              <BsPlusLg color="black" size={'45px'} />
            </div>
          </div>
          <div className="text-center  text-lg font-bold">
            숙소를 추가해 주세요
          </div>
        </div>
        {ghAdminData.map((el, i) => {
          const data = {
            imgSrc: process.env.REACT_APP_SERVER_URL + el.guestHouseImage[0],
            name: el.guestHouseName,
            price: el.rooms[0].roomPrice,
            star: el.guestHouseStar,
            tags: el.guestHouseTag,
            id: el.guestHouseId,
          };
          return (
            <div key={i}>
              <GuesthouseCard guesthouse={data} />
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default GhAdminList;
