import { BsPlusLg } from 'react-icons/bs';
import GuesthouseCard from '../common/GuesthouseCard';

interface Props {
  testGh: {
    imgSrc: string;
    name: string;
    price: number;
    star: number;
    tags: Array<string>;
    id: number;
  }[];
}
const GhAdminList = ({ testGh }: Props) => {
  const addGhHandler = () => {
    //useNavigate 사용 예정
    console.log('숙소 등록창으로 이동 ');
  };
  return (
    <div>
      <div className="text-center text-xl m-[20px]">
        <span className=" font-bold">{testGh[0].id}</span>
        님의 숙소 리스트
      </div>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-[20px] w-full h-full">
        <div className="w-full h-full">
          <div
            onClick={addGhHandler}
            className="aspect-[27/25] relative  bg-zinc-300 rounded-[15px]"
          >
            <div className=" absolute top-[50%] left-[50%] translate-x-[-50%] translate-y-[-50%]">
              <BsPlusLg color="black" size={'45px'} />
            </div>
          </div>
          <div className="text-center  text-lg font-bold">
            숙소를 추가해 주세요
          </div>
        </div>
        {testGh.map((el, i) => (
          <div key={i}>
            <GuesthouseCard guesthouse={el} />
          </div>
        ))}
      </div>
    </div>
  );
};
export default GhAdminList;
