import React, { useState } from 'react';
import RatedStar from '../RatedStar';
import { BiMessage } from 'react-icons/bi';
import EditReply from './EditReply';

type Props = {
  reviewComment?: {
    userName: string;
    createBy: string;
    comment: string;
    ProfileImg: string;
    starScore: number;
    admin?: string;
    adminComment?: Reply[];
    id?: number; // 해당 댓글 고유 아이디
  };
  type?: string;
};
type Reply = {
  replyComment: string;
  createBy: string;
};

export default function DetailReview({ reviewComment, type }: Props) {
  // 전역 변수 로그인된 사용자(관리자)
  const currentUser = 'mk';
  const [openComment, setOpenComment] = useState<boolean>(false);
  return (
    <>
      <div className="flex flex-row">
        {type && ['roomDetail', 'reviewPage'].includes(type) && (
          <div className="flex flex-col justify-between">
            <div className="w-[40px] mr-[10px]">
              <img
                src={reviewComment && reviewComment.ProfileImg}
                className="rounded-full w-[40px] mr-[10px] mb-[10px]"
              />
            </div>
            {type === 'reviewPage' &&
              reviewComment &&
              reviewComment.admin === currentUser && (
                <div
                  className="text-sm text-font-color cursor-pointer"
                  onClick={() => {
                    setOpenComment(!openComment);
                  }}
                >
                  답글 작성
                </div>
              )}
          </div>
        )}
        <div className="flex flex-col">
          {type === 'roomDetail' ? (
            <div className="flex flex-col justify-between">
              <div className="flex">
                <p className="mr-[5px] text-base font-bold">
                  {reviewComment && reviewComment.userName}
                </p>
                {reviewComment && <RatedStar star={reviewComment.starScore} />}
              </div>
              <p className="text-font-color text-sm">
                {reviewComment && reviewComment.createBy}
              </p>
            </div>
          ) : (
            <>
              <div className="flex items-center mb-15px ">
                <p className="mr-[10px] text-base font-bold">
                  {reviewComment && reviewComment.userName}
                </p>
                <p className="text-font-color text-sm mr-[5px]">
                  {reviewComment && reviewComment.createBy}
                </p>
                {reviewComment && <RatedStar star={reviewComment.starScore} />}
              </div>
              <div>{reviewComment && reviewComment.comment}</div>
              {type === 'reviewPage' &&
                reviewComment &&
                reviewComment.adminComment &&
                reviewComment.adminComment.length > 0 && (
                  <ul className="mt-[20px] flex flex-col">
                    <ul className="flex items-center">
                      <BiMessage className="mr-[10px]" />
                      <p className="font-bold mb-[5px]">사장님 댓글</p>
                    </ul>

                    {reviewComment.adminComment.map((reply, idx) => (
                      <div
                        key={idx}
                        className="flex flex-row ml-[26px] items-center"
                      >
                        <li>{reply.replyComment}</li>
                        <li className="ml-[14px] text-sm">{reply.createBy}</li>
                      </div>
                    ))}
                  </ul>
                )}
            </>
          )}
        </div>
      </div>
      <div>
        {openComment && reviewComment && reviewComment.id && (
          <EditReply type="admin" id={reviewComment.id} />
        )}
      </div>
      {type && type === 'roomDetail' && (
        <div>{reviewComment && reviewComment.comment}</div>
      )}
    </>
  );
}
