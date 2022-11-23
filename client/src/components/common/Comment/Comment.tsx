import React from 'react';
import BottomLabel from './BottomLabel';
import TopLabel from './TopLabel';

type Props = {
  houseName: string;
  date: string;
  imgsrc: string;
  room: string;
  roomLink: string;
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

export default function Comment({
  houseName,
  date,
  imgsrc,
  room,
  roomLink,
  reviewLink,
  reviewComment,
  type,
}: Props) {
  return (
    <div className="flex flex-col border border-border-color max-w-[1050px] min-h-[180px] rounded-CommentRadius">
      {/* 게스트하우스 이름, 이용 정보, 이동될 숙소 링크를 할당해주면 됩니다 */}
      <TopLabel houseName={houseName} date={date} roomLink={roomLink} />
      <BottomLabel
        imgsrc={imgsrc}
        houseName={houseName}
        room={room}
        reviewLink={reviewLink && reviewLink}
        reviewComment={reviewComment && reviewComment}
        type={type}
      />
    </div>
  );
}
