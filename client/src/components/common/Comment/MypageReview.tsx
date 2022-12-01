import React from 'react';
import Details from './Details';
import TopLabel from './TopLabel';
interface Review {
  guestHouseName: string;
  roomName: string;
  roomLink: number;
  roomImageUrl: string;
  roomReservationStart: string;
  roomReservationEnd: string;
  comment: string;
  createdAt: string;
  memberNickname: string;
}
export default function MypageReview({
  guestHouseName,
  roomName,
  roomLink,
  roomImageUrl,
  roomReservationStart,
  roomReservationEnd,
  comment,
  createdAt,
  memberNickname,
}: Review) {
  return (
    <div className="flex flex-col border border-border-color max-w-[1050px] min-h-[180px]  rounded-CommentRadius">
      <div className="h-[45px]">
        <TopLabel
          houseName={guestHouseName}
          date={`${roomReservationStart} ~ ${roomReservationEnd}`}
          roomLink={`/ghdetail/${roomLink}`}
        />
      </div>
      <div className="p-[22px] flex text-base">
        <img
          src={`${process.env.REACT_APP_SERVER_URL}${roomImageUrl}`}
          className="rounded-ImgRadius w-[90px] h-[90px]"
        />
        <div className="flex flex-col justify-between w-[100%] ml-3">
          <div className="mb-[15px]">
            <div>{guestHouseName}</div>
            <div>{roomName}</div>
          </div>
          <div className="flex flex-col justify-between">
            <div className="flex items-center">
              <p className="mr-[5px] text-base font-bold ">{memberNickname}</p>
              <p className="text-font-color text-sm ">{createdAt}</p>
            </div>
            <div>{comment}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
