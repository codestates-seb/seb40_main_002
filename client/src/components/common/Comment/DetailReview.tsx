import React, { useState } from 'react';
import RatedStar from '../RatedStar';
import { BiMessage } from 'react-icons/bi';
import EditReply from './EditReply';
interface reviewComment {
  createdAt: string;
  modifiedAt: string;
  reviewComment: string;
  reviewCommentId: number;
}

interface Member {
  memberBirth: string;
  memberEmail: string;
  memberId: string;
  memberImageUrl: string;
  memberNationality: string;
  memberNickname: string;
  memberPhone: string;
  memberRegisterKind: string;
  memberTag: string[] | null;
}

interface Props {
  reviewComment: {
    comment: string;
    createdAt: string;
    guestHouseMemberId: string;
    guestHouseName: string;
    member: Member;
    modifiedAt: string;
    reviewComment: reviewComment;
    reviewId: number;
    star: number; // 해당 댓글 고유 아이디
  };

  type: string;
}

export default function DetailReview({ reviewComment, type }: Props) {
  // 전역 변수 로그인된 사용자(관리자)
  const currentUser = '업주';
  const [openComment, setOpenComment] = useState<boolean>(false);
  return (
    <>
      <div className="flex flex-row">
        {type && ['roomDetail', 'reviewPage'].includes(type) && (
          <div className="flex flex-col justify-between">
            <div className="w-[40px] mr-[10px]">
              <img
                src={reviewComment && reviewComment.member.memberImageUrl}
                className="rounded-full w-[40px] mr-[10px] mb-[10px]"
              />
            </div>
            {type === 'reviewPage' &&
              reviewComment &&
              reviewComment.guestHouseMemberId === currentUser && (
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
                  {reviewComment && reviewComment.member.memberNickname}
                </p>
                {reviewComment && <RatedStar star={reviewComment.star} />}
              </div>
              <p className="text-font-color text-sm">
                {reviewComment && reviewComment.createdAt}
              </p>
            </div>
          ) : (
            <>
              <div className="flex items-center mb-15px ">
                <p className="mr-[10px] text-base font-bold">
                  {reviewComment && reviewComment.member.memberNickname}
                </p>
                <p className="text-font-color text-sm mr-[5px]">
                  {reviewComment && reviewComment.createdAt}
                </p>
                {reviewComment && <RatedStar star={reviewComment.star} />}
              </div>
              <div>{reviewComment && reviewComment.comment}</div>
              {type === 'reviewPage' &&
                reviewComment &&
                reviewComment.reviewComment && (
                  <ul className="mt-[20px] flex flex-col">
                    <ul className="flex items-center">
                      <BiMessage className="mr-[10px]" />
                      <p className="font-bold mb-[5px]">사장님 댓글</p>
                    </ul>

                    <div className="flex flex-row ml-[26px] items-center">
                      <li>{reviewComment.reviewComment.reviewComment}</li>
                      <li className="ml-[14px] text-sm">
                        {reviewComment.reviewComment.createdAt}
                      </li>
                    </div>
                  </ul>
                )}
            </>
          )}
        </div>
      </div>
      <div>
        {openComment && reviewComment && reviewComment.reviewId && (
          <EditReply type="admin" id={reviewComment.reviewId} />
        )}
      </div>
      {type && type === 'roomDetail' && (
        <div>{reviewComment && reviewComment.comment}</div>
      )}
    </>
  );
}
