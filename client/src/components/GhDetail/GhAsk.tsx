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
      <div>문의하기</div>
      <div className="flex justify-between items-center mt-[20px]">
        <div>{ghName}</div>
        <div className="flex justify-center items-center text-lg bg-point-color text-white rounded-btnRadius w-[200px] h-[40px]">
          {GhPhone}
        </div>
      </div>
    </div>
  );
};
export default GhAsk;
