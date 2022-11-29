import React from 'react';
import { useNavigate } from 'react-router-dom';
import DetailReview from './DetailReview';

interface Props {
  houseName: string;
  room: string;
  reviewLink?: string | null;
  type: string;
  reviewComment?: {
    comment: string;
    createdAt: string;
    star: number;
  };
}

export default function Details({
  houseName,
  room,
  reviewLink,
  reviewComment,
  type,
}: Props) {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col justify-between pl-[10px] h-[100%]">
      <div className="mb-[15px]">
        <div>{houseName}</div>
        <div>{room}</div>
      </div>

      {reviewLink && (
        <div
          className="cursor-pointer text-font-color"
          onClick={() => {
            navigate(`${reviewLink}`);
          }}
        >
          리뷰 작성
        </div>
      )}
    </div>
  );
}
