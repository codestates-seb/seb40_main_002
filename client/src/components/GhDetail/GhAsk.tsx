import CommonBtn from '../common/CommonBtn/CommonBtn';

const GhAsk = () => {
  return (
    <div className="w-6/12">
      <div>문의하기</div>
      <div className="flex justify-between items-center mt-[20px]">
        <div>숙소이름</div>
        <CommonBtn text={'010-1234-1234'} btnSize={'w-[300px] h-[40px]'} />
      </div>
    </div>
  );
};
export default GhAsk;
