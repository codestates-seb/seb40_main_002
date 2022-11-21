import CommonBtn from '../common/CommonBtn/CommonBtn';
interface AskProps {
  ghName?: string;
  GhPhone?: string;
}
const GhAsk = ({
  ghName = '정우네 게스트 하우스',
  GhPhone = '010-1234-5678',
}: AskProps) => {
  return (
    <div className="w-6/12">
      <div className="font-bold ">문의하기</div>
      <div className="text-font-color text-lg flex justify-between items-center mt-[20px]">
        <div>{ghName}</div>
        <div className=" text-lg flex justify-center items-center  bg-point-color text-white rounded-btnRadius w-[200px] h-[40px]">
          {GhPhone}
        </div>
      </div>
    </div>
  );
};
export default GhAsk;
