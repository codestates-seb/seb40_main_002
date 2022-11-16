import React from 'react';
import RatedStar from '../RatedStar';

type Props = {
  reviewComment?: {
    userName: string;
    createBy: string;
    comment: string;
    ProfileImg: string;
    starScore: number;
  };
  type?: string;
};

export default function DetailReview({ reviewComment, type }: Props) {
  return (
    <>
      <div className="flex flex-row">
        {type && ['roomDetail', 'reviewPage'].includes(type) && (
          <img
            src={reviewComment && reviewComment.ProfileImg}
            className="w-[40px] h-[40px] rounded-full mr-[10px] mb-[10px]"
          />
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
              <div className="flex items-center mb-15px">
                <p className="mr-[10px] text-base font-bold">
                  {reviewComment && reviewComment.userName}
                </p>
                <p className="text-font-color text-sm mr-[5px]">
                  {reviewComment && reviewComment.createBy}
                </p>
                {reviewComment && <RatedStar star={reviewComment.starScore} />}
              </div>
              <div>
                {reviewComment && reviewComment.comment} Lorem ipsum dolor, sit
                amet consectetur adipisicing elit. Laudantium harum non, itaque
                suscipit ut perspiciatis atque dicta. Hic minima autem,
                laboriosam porro commodi veniam. Impedit numquam soluta beatae
                molestias repellat.
              </div>
            </>
          )}
        </div>
      </div>
      {type && type === 'roomDetail' && (
        <div>
          {reviewComment && reviewComment.comment} Lorem ipsum dolor, sit amet
          consectetur adipisicing elit. Laudantium harum non, itaque suscipit ut
          perspiciatis atque dicta. Hic minima autem, laboriosam porro commodi
          veniam. Impedit numquam soluta beatae molestias repellat.
        </div>
      )}
    </>
  );
}
