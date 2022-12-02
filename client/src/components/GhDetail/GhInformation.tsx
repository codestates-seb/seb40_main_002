import React from 'react';
import RatedStar from '../common/RatedStar';
import Tag from '../common/Tag';
import Heart from '../common/Heart';
import Carousel from '../common/Carousel/Carousel';
import { useNavigate } from 'react-router-dom';
type TagProps = {
  ghName: string;
  ghInfo: string;
  ghMemberId: string;
  tags: string[];
  ghImage: string[];
  userMemberId: string;
  guestHouseId: number;
  guestHouseStar: number;
};
const GhInformation = ({
  tags,
  ghInfo,
  ghName,
  ghImage,
  ghMemberId,
  userMemberId,
  guestHouseId,
  guestHouseStar,
}: TagProps) => {
  const navigate = useNavigate();
  const admin = ghMemberId === userMemberId;
  const editHandler = () => {
    navigate(`/ghedit/${guestHouseId}`);
  };
  const commentHandler = () => {
    navigate(`/review/${guestHouseId}`);
  };

  return (
    <>
      <div className="font-bold">{ghName}</div>
      <div className=" flex my-[10px] h-[30px] gap-[4px] justify-between text-font-color">
        <div className="flex">
          <RatedStar star={guestHouseStar} />
          <div className="ml-[20px] flex">
            {tags.map((el: string, i: number) => (
              <Tag name={el} key={i} />
            ))}
          </div>
        </div>
        <div>
          {admin ? (
            <div className="text-lg flex gap-[20px] ">
              <div onClick={editHandler} className="cursor-pointer">
                수정하기
              </div>
              <div onClick={commentHandler} className="cursor-pointer">
                후기 관리
              </div>
            </div>
          ) : (
            <Heart id={guestHouseId} />
          )}
        </div>
      </div>
      <div className="max-w-full ">
        <Carousel images={ghImage} />
      </div>
      <div className="border-b-[2px]">
        <div className="mt-[40px] mb-[20px] font-bold ">숙소정보</div>
        <div className="text-lg  mb-[20px]  text-lg">{ghInfo}</div>
      </div>
    </>
  );
};

export default GhInformation;
