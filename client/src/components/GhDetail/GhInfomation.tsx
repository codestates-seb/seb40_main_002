import React from 'react';
import RatedStar from '../common/RatedStar';
import Tag from '../common/Tag';
import Heart from '../common/Heart';
import Carousel from '../common/Carousel/Carousel';
type Props = {
  tags: string[];
};
const GhInfomation = ({ tags }: Props) => {
  //api 명세가 정해지면 수정예정입니다!!
  return (
    <>
      <div className="">정우게스트하우스</div>
      <div className=" flex my-[10px] h-[30px] gap-[4px] justify-between">
        <div className="flex">
          <RatedStar star={3}></RatedStar>
          <div className="ml-[20px] flex">
            {tags.map((el: string, i: number) => (
              <Tag name={el} key={i}></Tag>
            ))}
          </div>
        </div>
        <div>
          <Heart id={3}></Heart>
        </div>
      </div>
      <div className="max-w-[1120px] ">
        <Carousel></Carousel>
      </div>
      <div>
        <div className="my-[20px]">숙소정보</div>
        <div className="text-lg max-w-[1120px]">
          우리 정우 게스트하우스는요 편의점 1분거리 버스타고 금방와요 사람들이
          착해요 고양이가 있어요 우도 선착장 30분 거리 숙소가 이동되요 전용
          주차장이 있어요 인스타로 서로를 인증해요
        </div>
      </div>
    </>
  );
};

export default GhInfomation;
