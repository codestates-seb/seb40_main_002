interface AskProps {
  ghName: string;
  GhPhone: string;
}
const GhAsk = ({ ghName, GhPhone }: AskProps) => {
  return (
    <div className="w-full md:w-[50%] h-[300px]">
      <div className="font-bold ">문의하기</div>
      <div className="gap-[10px] text-font-color text-lg md:flex md:justify-between flex justify-center items-center mt-[20px]">
        <div>{ghName}</div>
        <div className=" text-lg flex justify-center items-center  bg-point-color text-white rounded-btnRadius w-[200px] h-[40px]">
          {GhPhone}
        </div>
      </div>
    </div>
  );
};
export default GhAsk;
