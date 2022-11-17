import React from 'react';
import Details from './Details';

// { imgsrc }
type Props = {
  imgsrc: string;
  houseName: string;
  room: string;
  reviewLink?: string | null;
  type: string;
  reviewComment?: {
    userName: string;
    createBy: string;
    comment: string;
    ProfileImg: string;
    starScore: number;
  };
};

export default function BottomLabel({
  imgsrc,
  houseName,
  room,
  reviewLink,
  reviewComment,
  type,
}: Props) {
  return (
    <div className="p-[22px] flex text-base">
      <img src={`${imgsrc}`} className="rounded-ImgRadius w-[90px] h-[90px]" />
      <div className="flex justify-between w-[100%] items-center">
        <Details
          houseName={houseName}
          room={room}
          reviewLink={reviewLink && reviewLink}
          reviewComment={reviewComment && reviewComment}
          type={type}
        />
      </div>
    </div>
  );
}
