import React from 'react';
import RatedStar from '../common/RatedStar';
import Tag from '../common/Tag';
import Heart from '../common/Heart';
import Carousel from '../common/Carousel/Carousel';
type TagProps = {
  ghName: string;
  ghInfo: string;
  tags: string[];
  ghImage: string[];
};
const GhInformation = ({ tags, ghInfo, ghName, ghImage }: TagProps) => {
  //api 명세가 정해지면 수정예정입니다!!
  const editHandler = () => {
    console.log('수정페이지 이동');
  };
  const commentHandler = () => {
    console.log('후기페이지 이동');
  };
  //관리자 확인 임시
  //숙소관리자 아이디===로그인 된 유저 아이디 비교해서 숙소관리자면 수정,후기 관리 버튼 유저면 하트 컴포넌트
  const user = true;
  return (
    <>
      <div className="font-bold">{ghName}</div>
      <div className=" flex my-[10px] h-[30px] gap-[4px] justify-between text-font-color">
        <div className="flex">
          <RatedStar star={3} />
          <div className="ml-[20px] flex">
            {tags.map((el: string, i: number) => (
              <Tag name={el} key={i} />
            ))}
          </div>
        </div>
        <div>
          {user ? (
            <Heart id={3} />
          ) : (
            <div className="text-lg flex gap-[20px] ">
              <div onClick={editHandler} className="cursor-pointer">
                수정하기
              </div>
              <div onClick={commentHandler} className="cursor-pointer">
                후기 관리
              </div>
            </div>
          )}
        </div>
      </div>
      <div className="max-w-full ">
        <Carousel images={ghImage} />
      </div>
      <div className="border-b-[2px]">
        <div className="mt-[40px] mb-[20px] font-bold ">숙소정보</div>
        <div className="text-lg max-w-[1120px] mb-[20px]  text-lg">
          {ghInfo}
        </div>
      </div>
    </>
  );
};

export default GhInformation;
